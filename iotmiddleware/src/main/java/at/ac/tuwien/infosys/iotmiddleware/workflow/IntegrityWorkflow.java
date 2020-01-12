package at.ac.tuwien.infosys.iotmiddleware.workflow;

import at.ac.tuwien.infosys.iotmiddleware.client.BlockchainClient;
import at.ac.tuwien.infosys.iotmiddleware.model.Message;
import at.ac.tuwien.infosys.iotmiddleware.utils.Defines;
import at.ac.tuwien.infosys.iotmiddleware.utils.JsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import jdk.nashorn.internal.ir.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class IntegrityWorkflow extends Workflow{

    private final static Logger logger = LoggerFactory.getLogger(IntegrityWorkflow.class);

    private BlockchainClient client;

    public IntegrityWorkflow(Message message) {
        super(message);
    }

    @Override
    public void run() {

        byte[] json = null;

        try {
            client = BlockchainClient.getInstance();
            IPFS ipfs = new IPFS(Defines.IFPS_ADDRESS);

            json = JsonConverter.toJson(message);
            String name = String.format("%s_%d.json", message.getId(), message.getTimestamp());
            NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(name, json);
            MerkleNode addResult = ipfs.add(file).get(0);
            logger.info("Stored file {} into ipfs with hash {}.", name, addResult.hash);

            Multihash hash = addResult.hash;
            byte[] longHash =  hash.toBytes();
            byte[] digest = Arrays.copyOfRange(longHash, 2, longHash.length);

            byte[] hashedId = null;
            try {
                MessageDigest dig = MessageDigest.getInstance("SHA-256");
                hashedId = dig.digest(message.getId().getBytes(StandardCharsets.UTF_8));
            } catch (NoSuchAlgorithmException e) {
                logger.error("Error while instantianting digester for SHA-256");
                //This error shall not occur, hence fail hard!
                System.exit(0);
            }

            CompletableFuture<TransactionReceipt> result = client.sendTransaction(hash.type.index, hash.type.length, digest, hashedId);

            if(result != null){
                TransactionReceipt receipt = result.get();
                logger.info("Received transaction status {} for message {}.", receipt.getStatus(), message);
            }
        } catch (IOException e) {
            logger.error("Could not send message {} to bc.", message, e);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error occurred while waiting for transaction receipt.", e);
        }
    }
}
