package at.ac.tuwien.infosys.iotclient.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class Measurement {

    private Unit unit;
    private long timestamp;
    private long value;

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString(){
      LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getTimeZone("UTC").toZoneId());
      return value+" "+unit+" ("+time+")";
    }
}
