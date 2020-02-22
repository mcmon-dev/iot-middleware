package at.ac.tuwien.infosys.iotclient.model;

public class Message {

    private String id;
    private double value;
    private long timestamp;
    private Unit unit;
    private MeasureType type;

    public Message(){
        super();
    }

    public Message(String id, double value, long timestamp, Unit unit, MeasureType type) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
        this.unit = unit;
        this.type = type;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (Double.compare(message.value, value) != 0) return false;
        if (timestamp != message.timestamp) return false;
        if (id != null ? !id.equals(message.id) : message.id != null) return false;
        if (unit != message.unit) return false;
        return type == message.type;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "at.ac.tuwien.infosys.iotclient.model.Message{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                ", unit=" + unit +
                ", type=" + type +
                '}';
    }
}
