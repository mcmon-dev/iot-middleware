package at.ac.tuwien.infosys.iotclient.model;

import java.util.HashMap;
import java.util.Map;

public enum MeasureType {
    Temperature ("/temperature"),
    Humidity ("/humidity");

    private final String uri;
    private static final Map<String, MeasureType> lookup = new HashMap<>();

    static {
        for(MeasureType t : MeasureType.values()){
            lookup.put(t.uri, t);
        }
    }

    MeasureType(String uri) {
        this.uri = uri;
    }

    public static MeasureType mapToEnum(String uri){
        return lookup.get(uri);
    }
}
