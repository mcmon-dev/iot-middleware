package at.ac.tuwien.infosys.iotclient;

import at.ac.tuwien.infosys.iotclient.model.CmdOptions;
import at.ac.tuwien.infosys.iotclient.subscriber.IntegrityChannelSubscriber;
import at.ac.tuwien.infosys.iotclient.subscriber.RealtimeChannelSubsciber;
import at.ac.tuwien.infosys.iotclient.utils.ArgumentHandler;
import at.ac.tuwien.infosys.iotclient.utils.Defines;
import at.ac.tuwien.infosys.iotclient.utils.LogWriter;
import at.ac.tuwien.infosys.iotclient.utils.TransactionCommitChecker;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static boolean closed;

    public static void main(String[] args) {
        logger.error("Activated");
        logger.warn("Activated");
        logger.info("Activated");
        logger.debug("Activated");
        logger.trace("Activated");

        logger.info("*****************************************************************");
        logger.info("****************** STARTED IOT CLIENT ***************************");
        logger.info("*****************************************************************");

        MqttClient mqttClient = null;
        CmdOptions options = null;
        IntegrityChannelSubscriber integrityClient = null;
        RealtimeChannelSubsciber realTimeSubscriber = null;

        try {
            ArgumentHandler handler = new ArgumentHandler();
            options = handler.parse(args);
            logger.info("Staring to connect to broker {}.", Defines.BROKER);
            realTimeSubscriber = new RealtimeChannelSubsciber();
            realTimeSubscriber.connect();
            logger.info("Starting to subscribe to topics.");
            if(options.getId() == null){
                realTimeSubscriber.subscribe(Defines.REALTIME_TOPICS);
            } else {
                realTimeSubscriber.subscribe(options.getId(), options.getAmount());
            }

            logger.info("Starting to listen for event of contract {}.", Defines.CONTRACT_ADDRESS);
            integrityClient = new IntegrityChannelSubscriber();
            integrityClient.startEventListener();

            Thread.sleep(options.getDuration());

            closeRunningClients(realTimeSubscriber, integrityClient);

            //Start checking histories and write csv files
            TransactionCommitChecker cleaner = new TransactionCommitChecker();
            cleaner.checkCommits(IntegrityChannelSubscriber.logs, IntegrityChannelSubscriber.blocks);
            logger.debug("Starting to write logs into files.");
            LogWriter.writeLogsToFile(options.getFileName(), RealtimeChannelSubsciber.logs, IntegrityChannelSubscriber.logs);
        } catch (MqttException e) {
           logger.error("Client shuts down because a fatal error occurred.", e);
        } catch (IOException e) {
            logger.error("Error occured while listing to events", e);
        } catch (InterruptedException e) {
            logger.info("Thread was interrupted.", e);
        } finally {
            closeRunningClients(realTimeSubscriber, integrityClient);
        }
    }

    private static void closeRunningClients(RealtimeChannelSubsciber realtimeChannelSubsciber, IntegrityChannelSubscriber integrityClient) {

        if (!closed) {
            if (realtimeChannelSubsciber != null) {

                    logger.debug("Starting to close MQTT client.");
                    realtimeChannelSubsciber.close();
            }

            if (integrityClient != null) {
                logger.debug("Starting to close integrity client.");
                integrityClient.close();
            }

            closed = true;
        }
    }

}
