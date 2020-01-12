package at.ac.tuwien.infosys.iotmiddleware.workflow;

import at.ac.tuwien.infosys.iotmiddleware.model.Message;

public abstract class Workflow implements Runnable{

    protected Message message;

    public Workflow(Message message) {
        this.message = message;
    }
}
