package at.ac.tuwien.infosys.iotmiddleware.server;

import at.ac.tuwien.infosys.iotmiddleware.client.CoAPClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistryResource extends CoapResource {

    private static final Logger logger = LoggerFactory.getLogger(RegistryResource.class);

    private ExecutorService executor;

    public RegistryResource() {
        super("register");
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        String text = exchange.getRequestText();
        logger.debug("Received text: {}", text);

        try {
            registerSensor(text);
            exchange.respond(ResponseCode.CREATED);
        } catch (URISyntaxException e) {
           logger.error("", e);
            exchange.respond(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void registerSensor(String path) throws URISyntaxException {
        URI uri = new URI(path);
        CoAPClient client = new CoAPClient(uri, executor);
        executor.execute(client);
        logger.info("Client has started.");
    }
}
