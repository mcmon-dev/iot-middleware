package at.ac.tuwien.infosys.iotmiddleware.client;

import at.ac.tuwien.infosys.iotmiddleware.utils.Defines;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTClient {

    private static MQTTClient instance;
    private final static Logger logger = LoggerFactory.getLogger(MQTTClient.class);

    public synchronized static MQTTClient getInstance() throws MqttException {

        if (instance == null) {
            instance = new MQTTClient();
            instance.clientId = MqttClient.generateClientId();
            instance.client = new MqttClient(Defines.BROKER, instance.clientId, new MqttDefaultFilePersistence());
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setMaxInflight(Defines.MAX_INFLIGHTS);
            instance.client.connect(connectOptions);
            logger.info("Connected to broker {} with Id {}.", Defines.BROKER, instance.clientId);
        }
        return instance;
    }

    private MqttClient client;
    private String clientId;

    private MQTTClient() {    }

    public void publish(String topic, byte[] payload) {
        try {
            client.publish(topic, payload, 2, false);
        } catch (MqttException e) {
            logger.error("Error occurred during publishing message {}.", e);
        }
    }

    public static void close() {
        if (instance != null && instance.client != null) {
            try {
                instance.client.disconnect();
                instance.client.close();
            } catch (MqttException e) {
                logger.error("Error occurred while closing MQTT at.ac.tuwien.infosys.iotmiddleware.client {}.", instance.clientId, e);
            }
        }
    }
}
