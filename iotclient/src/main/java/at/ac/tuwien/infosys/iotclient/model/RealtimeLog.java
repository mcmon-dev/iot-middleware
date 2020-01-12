package at.ac.tuwien.infosys.iotclient.model;

public class RealtimeLog extends LogMessage {

    private long tsReceived;

    public RealtimeLog(Message message, long tsReceived){
        super(message, Channel.REALTIME, tsReceived - message.getTimestamp());
        this.tsReceived = tsReceived;
    }

    public long getTsReceived() {
        return tsReceived;
    }
}
