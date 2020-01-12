package at.ac.tuwien.infosys.iotmiddleware.model;

public class Message {

    private String id;
    private double value;
    private long timestamp;
    private Unit unit;
    private MeasureType type;

    public Message(String uri, MeasureType type, Measurement measurement){
        id = uri;
        this.type = type;
        value = measurement.getValue();
        timestamp = measurement.getTimestamp();
        unit = measurement.getUnit();
    }

    public String getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Unit getUnit() {
        return unit;
    }

    public MeasureType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                ", unit=" + unit +
                ", type=" + type +
                '}';
    }
}
