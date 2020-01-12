import com.fasterxml.jackson.databind.ObjectMapper;
import at.ac.tuwien.infosys.iotmiddleware.model.Measurement;
import at.ac.tuwien.infosys.iotmiddleware.model.Unit;
import io.ipfs.api.NamedStreamable;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class MessageTest {

    @Test
    public void messageUnmarshallTest() throws IOException {
        String json = "{\"value\": 30, \"unit\": \"percent\", \"timestamp\": 1522423450}";
        ObjectMapper mapper = new ObjectMapper();
        Measurement measurement = mapper.readValue(json, Measurement.class);

        assertTrue(measurement.getValue() == 30);
        assertTrue(measurement.getUnit() == Unit.percent);
        assertTrue(measurement.getTimestamp() == 1522423450);
    }

    @Test
    public void testHaht() throws IOException {
      /*  Multihash filePointer = Multihash.fromBase58("QmYpW8LW7QmV8fUuiRGiXfXQTGGJ9mbpmkxt2Dhfcdt2DG");
        byte[] longHash = filePointer.toBytes();
        byte[] hash = Arrays.copyOfRange(longHash, 2, longHash.length);

        System.out.println(Arrays.toString(hash));
        System.out.println(hash.length);
        System.out.println(filePointer.toHex());
        */
        byte[] json =  "{\"unit\":\"degree\",\"timestamp\":1524746826400,\"value\":23.9}".getBytes();
        String name = "test.json";
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(name, json);
        System.out.println(Arrays.equals(file.getContents(), json));
    }
}
