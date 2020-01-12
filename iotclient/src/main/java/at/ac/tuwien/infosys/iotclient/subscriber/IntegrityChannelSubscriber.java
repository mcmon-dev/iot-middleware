package at.ac.tuwien.infosys.iotclient.subscriber;

import at.ac.tuwien.infosys.iotclient.model.IntegrityLog;
import at.ac.tuwien.infosys.iotclient.model.Message;
import at.ac.tuwien.infosys.iotclient.utils.Defines;
import at.ac.tuwien.infosys.iotclient.utils.JsonConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ipfs.api.IPFS;
import io.ipfs.multibase.Base58;
import io.ipfs.multihash.Multihash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.utils.Async;
import rx.Subscription;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;

public class IntegrityChannelSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(IntegrityChannelSubscriber.class);
    public static Queue<IntegrityLog> logs = new ConcurrentLinkedQueue<>();
    public static Map<EthBlock.Block, Long>  blocks = new ConcurrentHashMap<>();

    private Event contractEvent;
    private List<Subscription> subscriptions;
    private ScheduledExecutorService executorService;
    private Web3j web3;

    public IntegrityChannelSubscriber(){
        contractEvent = new Event("MeasurementUpdate", Arrays.asList(new TypeReference<Address>() {}), Arrays.asList(new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}, new TypeReference<Bytes32>() {}));
        subscriptions = new ArrayList<>();
    }

    public void startEventListener() throws IOException{

        executorService = Async.defaultExecutorService();
        web3 = Web3j.build(new UnixIpcService(Defines.IPC_PATH), 1l, executorService);


        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();

        EthFilter filter = new EthFilter(DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST, Defines.CONTRACT_ADDRESS);

        //Create observer for new blocks in the chain.
        Subscription blockSubcription = web3.blockObservable(false).subscribe(block -> {
            long tsBlock = System.currentTimeMillis();
            blocks.put(block.getBlock(), tsBlock);
            logger.debug("Received new block {} with hash {}", block.getBlock().getNumber(), block.getBlock().getHash());
        });

        subscriptions.add(blockSubcription);

        //Create observer for new event of the smart contract IntegrityService
       Subscription eventSubscription = web3.ethLogObservable(filter).subscribe(log -> {
            long tsHash = System.currentTimeMillis();
            logger.debug("Logger data: {}", log.getData());
            List<Type> eventParams = FunctionReturnDecoder.decode(
                    log.getData(), contractEvent.getNonIndexedParameters());

            Multihash hash = decodeLogEvent(eventParams);

            logger.info("Received following hash: {} IsRemoved: {}", hash, log.isRemoved());

            IPFS ipfs = new IPFS(Defines.IPFS_ADDRESS);

            long tsFile = 0;
            Message message = null;
            try {
                byte[] fileContents = ipfs.cat(hash);
                logger.debug("Received following content from ipfs: {}, Bytes: {}", new String(fileContents), fileContents);
                tsFile = System.currentTimeMillis();
                message = JsonConverter.toJsonObject(fileContents, Message.class);
            } catch (IOException e) {
                logger.error("Could not receive file for hash {}.", hash, e);
                tsFile = 0;
                message = new Message("FAIL", 0,0, null, null);
            }

            IntegrityLog logEntry = new IntegrityLog(log.getTransactionHash(), message, tsHash, tsFile, log.getBlockNumber(), log.getBlockHash(), log.isRemoved());
            logs.add(logEntry);
        });

        subscriptions.add(eventSubscription);

        logger.info("Launched listing to smart contract.");
    }

    /**
     * Unsubscribes from block and smart contract observable.
     */
    public void close(){

        for(Subscription sub : subscriptions){
            if(sub != null){
                sub.unsubscribe();
            }
        }

        if(web3 != null) {
            web3.shutdown();
        }
    }

    private Multihash decodeLogEvent(List<Type> params){
        BigInteger functionCode = (BigInteger) params.get(0).getValue();
        byte[] digest = (byte[]) params.get(2).getValue();

        Multihash.Type functionType = Multihash.Type.lookup(functionCode.intValue());
        Multihash result = new Multihash(functionType, digest);
        return result;
    }
}
