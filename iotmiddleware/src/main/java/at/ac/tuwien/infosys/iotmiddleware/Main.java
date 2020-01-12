package at.ac.tuwien.infosys.iotmiddleware;

import at.ac.tuwien.infosys.iotmiddleware.client.BlockchainClient;
import at.ac.tuwien.infosys.iotmiddleware.server.RegistryCoAPServer;
import at.ac.tuwien.infosys.iotmiddleware.utils.Defines;
import at.ac.tuwien.infosys.iotmiddleware.client.MQTTClient;
import io.ipfs.api.IPFS;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args){
        logger.error("Activated");
        logger.warn("Activated");
        logger.info("Activated");
        logger.debug("Activated");
        logger.trace("Activated");

        logger.info("********************************************************");
        logger.info("************** STARTING IOT MIDDLEWARE *****************");
        logger.info("********************************************************");

        RegistryCoAPServer server = null;

        try {
            if (args.length > 0 && args[0].equals("-m")) {
                try {
                    Defines.MAX_INFLIGHTS = Integer.parseInt(args[1]);
                    logger.info("Set Max_Insights to {}.", Defines.MAX_INFLIGHTS);
                } catch (NumberFormatException e) {
                    logger.error("Could not read argument.", e);
                    System.exit(1);
                }
            }

            logger.info("Starting to connect to MQTT broker.");
            MQTTClient publisher = MQTTClient.getInstance();

            if (publisher == null) {
                logger.error("Could not connect to MQTTClient.");
                System.exit(1);
            }

            logger.info("Connected to broker {}.", Defines.BROKER);

            logger.info("Starting to connect to Blockchain client.");
            BlockchainClient client = BlockchainClient.getInstance();

            if (client == null) {
                logger.error("Could not connect to BlockchainClient.");
                System.exit(1);
            }

            logger.info("Starting to connect to ipfs client.");
            IPFS ipfs = new IPFS(Defines.IFPS_ADDRESS);

            try{
                ipfs.version();
            }catch (IOException e){
                logger.error("Could not connect to IPFS client.");
                System.exit(1);
            }

            logger.info("Connected to blockchain client {}", Defines.BC_ACCOUNT);

            logger.info("Starting Registry Server at 0.0.0.0:{}", Defines.REGISTRY_PORT);
            server = new RegistryCoAPServer();
            InetSocketAddress address = new InetSocketAddress("0.0.0.0", Defines.REGISTRY_PORT);
            server.addEndpoint(new CoapEndpoint(address));
            server.start();

            System.out.println("Press Q to terminate iot-middleware.");
            Scanner sc = new Scanner(System.in);
            boolean keepGoing = true;

            while(keepGoing){
                String sign = sc.next();
                if(sign.equals("Q")){
                    keepGoing = false;
                }
            }
        } catch (IOException | MqttException e){
            logger.error("", e);
        } finally {
            if(server != null){ server.stop();}
            logger.info("Closed RegistryServer.");
            MQTTClient.close();
            logger.info("Closed MQTT Client.");

            BlockchainClient.close();
            logger.info("Closed Ethereum client");
        }
    }
}
