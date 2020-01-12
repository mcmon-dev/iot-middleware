package at.ac.tuwien.infosys.iotmiddleware.client;

import at.ac.tuwien.infosys.iotmiddleware.utils.Defines;
import at.ac.tuwien.infosys.iotmiddleware.contract.IntegrityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

public class BlockchainClient {
    private static BlockchainClient instance;

    private final static Logger logger = LoggerFactory.getLogger(BlockchainClient.class);

    private Web3j web3;
    private IntegrityService contract;

    public static BlockchainClient getInstance() throws IOException {

        if (instance == null){
            instance = new BlockchainClient();
            instance.connect();
        }

        return instance;
    }

    private BlockchainClient() { }

    private void connect() throws IOException {
        web3 = Web3j.build(new HttpService());
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();

        ClientTransactionManager manager = new ClientTransactionManager(web3, Defines.BC_ACCOUNT);
        contract = IntegrityService.load(Defines.CONTRACT_ADDRESS, web3, manager, Defines.GAS_PRICE, Contract.GAS_LIMIT);

        logger.info("Client version: {} Contract {} validity: {}", clientVersion, Defines.CONTRACT_ADDRESS, contract.isValid());
    }

    public synchronized CompletableFuture<TransactionReceipt> sendTransaction(int functionCode, int digestLength, byte[] digest, byte[] hashedId){
        try {
            logger.info("Send following hash to contract({}): {}", Defines.CONTRACT_ADDRESS, new BigInteger(1, digest).toString(16));
            CompletableFuture<TransactionReceipt> futureTransactionReceipt = contract.update(BigInteger.valueOf(functionCode), BigInteger.valueOf(digestLength), digest, hashedId).sendAsync();
            return futureTransactionReceipt;
        }catch (Exception e) {
            logger.error("Error occurred during updating smart contract.", e);
        }
        return null;
    }

    public static void close(){
        if(instance != null && instance.web3 != null){
            instance.web3.shutdown();
        }
    }
}
