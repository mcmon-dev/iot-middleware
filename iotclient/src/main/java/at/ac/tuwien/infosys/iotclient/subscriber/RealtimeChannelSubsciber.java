package at.ac.tuwien.infosys.iotclient.subscriber;

import at.ac.tuwien.infosys.iotclient.model.Message;
import at.ac.tuwien.infosys.iotclient.model.RealtimeLog;
import at.ac.tuwien.infosys.iotclient.utils.JsonConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.ac.tuwien.infosys.iotclient.utils.Defines;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RealtimeChannelSubsciber {

    private static final Logger logger = LoggerFactory.getLogger(RealtimeChannelSubsciber.class);

    public static Queue<RealtimeLog> logs = new ConcurrentLinkedQueue<>();

    private String id;
    private MqttClient client;
    private ObjectMapper mapper;
    private List<String> subscribedTopics;

    public RealtimeChannelSubsciber() {
        mapper = new ObjectMapper();
    }

    public MqttClient connect() throws MqttException {
        id = MqttClient.generateClientId();
        client = new MqttClient(Defines.BROKER, id, new MemoryPersistence());
        client.connect();
        subscribedTopics  = new ArrayList<>();
        return client;
    }

    public void subscribe(String id, int amount){
        String[] topics = new String[amount];
        StringBuffer buffer = new StringBuffer();

        for(int i = 0; i < amount; i++){
            buffer.append("coap://").append(id).append("/").append(i);
            topics[i] = buffer.toString();
            buffer.setLength(0);
        }

        subscribe(topics);
    }

    /**
     * Client must already be initialized.
     * @param topics
     */
    public void subscribe(String[] topics){

        logger.debug("Subscribe to topics: {}", topics);

        for(String topic : topics){

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    logger.error("Error during subscription. ", throwable);
                }

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    long tsReceived = System.currentTimeMillis();
                    logger.info("Received message. ", new String(mqttMessage.getPayload()));
                    Message message = JsonConverter.toJsonObject(mqttMessage.getPayload(), Message.class);

                    RealtimeLog realtimeLog = new RealtimeLog(message, tsReceived);
                    logs.add(realtimeLog);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });

            try {
                client.subscribe(topic, 2);
                subscribedTopics.add(topic);
            } catch (MqttException e) {
                logger.error("Could not subscribe to topic {}.", topic, e);
            }
        }
    }

    public void close(){
        if(client != null){
            try {
                client.unsubscribe(subscribedTopics.toArray(new String[0]));
                client.disconnect();
                client.close();
            } catch (MqttException e) {
                logger.error("Error occured during closing of client {}.", id, e);
            }
        }
    }
}
