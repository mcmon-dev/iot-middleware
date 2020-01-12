package at.ac.tuwien.infosys.iotclient.model;

public abstract class LogMessage {

    protected Message message;
    protected Channel channel;
    /**
     * Is defined as the difference between the first timestamp and the last timestamp.
     */
    protected long delay;

    public LogMessage(Message message, Channel channel, long delay){
        this.message = message;
        this.channel = channel;
        this.delay = delay;
    }

    public boolean isSameMessage(Message msg){
        if(message.equals(msg)){
            return true;
        } else {
            return false;
        }
    }

    public Message getMessage() {
        return message;
    }

    public Channel getChannel() {
        return channel;
    }

    public long getDelay() { return delay; }
}
