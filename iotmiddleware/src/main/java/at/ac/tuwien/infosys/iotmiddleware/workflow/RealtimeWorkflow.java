package at.ac.tuwien.infosys.iotmiddleware.workflow;

import at.ac.tuwien.infosys.iotmiddleware.client.MQTTClient;
import at.ac.tuwien.infosys.iotmiddleware.model.Message;
import at.ac.tuwien.infosys.iotmiddleware.utils.JsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.concurrent.CompletableFuture;

public class RealtimeWorkflow extends Workflow {

    private final static Logger logger = LoggerFactory.getLogger(RealtimeWorkflow.class);

    private MQTTClient publisher;

    public RealtimeWorkflow(Message message){
       super(message);
    }

    @Override
    public void run() {
        try {
            publisher = MQTTClient.getInstance();
            byte[] payload = JsonConverter.toJson(message);
            publisher.publish(message.getId(), payload);
        } catch (MqttException | JsonProcessingException e) {
            logger.error("Could not send message {} to bc.", message, e);
        }
    }
}
