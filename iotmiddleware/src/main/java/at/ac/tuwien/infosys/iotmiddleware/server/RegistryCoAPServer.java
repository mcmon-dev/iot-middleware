package at.ac.tuwien.infosys.iotmiddleware.server;

import org.eclipse.californium.core.CoapServer;

public class RegistryCoAPServer  extends CoapServer {
    public RegistryCoAPServer(){
        add(new RegistryResource());
    }
}
