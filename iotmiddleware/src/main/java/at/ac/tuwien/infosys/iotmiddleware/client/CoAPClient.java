package at.ac.tuwien.infosys.iotmiddleware.client;

import at.ac.tuwien.infosys.iotmiddleware.model.MeasureType;
import at.ac.tuwien.infosys.iotmiddleware.model.Measurement;
import at.ac.tuwien.infosys.iotmiddleware.model.Message;
import at.ac.tuwien.infosys.iotmiddleware.utils.JsonConverter;
import at.ac.tuwien.infosys.iotmiddleware.workflow.IntegrityWorkflow;
import at.ac.tuwien.infosys.iotmiddleware.workflow.RealtimeWorkflow;
import org.eclipse.californium.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class CoAPClient implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(CoAPClient.class);

    private URI uri;
    private ExecutorService executor;

    public CoAPClient(URI uri, ExecutorService executor){
        this.uri = uri;
        this.executor = executor;
    }

    @Override
    public void run() {
        //Discover
        CoapClient client = new CoapClient(uri);
        logger.info("Trying to discover resources from {}", uri);
        Set<WebLink> links = client.discover();
        logger.info("Endpoint  {} has following resources: {}", uri, links);

        List<CoapClient> clients = new ArrayList<>();
        List<CoapObserveRelation> relations = new ArrayList<>();


        for(WebLink link : links){
            try {
                URI newURI= new URI(uri.toString()+""+link.getURI());
                logger.info("The new at.ac.tuwien.infosys.iotmiddleware.client will have following URI {}", newURI);
                CoapClient c = new CoapClient(newURI);

                CoapObserveRelation relation = c.observe(
                        new CoapHandler() {
                            @Override public void onLoad(CoapResponse response) {
                                String content = response.getResponseText();
                                try {
                                    Measurement measurement =  JsonConverter.toJsonObject(content, Measurement.class);
                                    Message message = new Message(newURI.toString(), MeasureType.mapToEnum(link.getURI()), measurement);
                                    executor.execute(new RealtimeWorkflow(message));
                                    logger.debug("Started RealtimeWorkflow ({}). Message: {}", newURI, message);
                                    executor.execute(new IntegrityWorkflow(message));
                                    logger.debug("Started IntegrityWorkflow ({}). {}", newURI, message);
                                } catch (IOException e) {
                                    logger.error("", e);
                                }
                            }

                            @Override public void onError() {
                               logger.error("Error occurred during observation of {}", c.getURI());
                            }
                        });
                relations.add(relation);
                clients.add(c);
            } catch (URISyntaxException e) {
                logger.error("", e);
            }
        }

        while(relations.stream().anyMatch(elem -> elem.isCanceled() == false));
        logger.info("All clients are shut down. {}", links);
        logger.info("Device {} is not connected anymore", uri);
    }
}
