import at.ac.tuwien.infosys.iotmiddleware.model.Measurement;
import at.ac.tuwien.infosys.iotmiddleware.model.Unit;
import at.ac.tuwien.infosys.iotmiddleware.utils.JsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class JsonConverterTest {

    @Test
    public void toJsonStringResultTest() throws JsonProcessingException {
        Measurement measurement = new Measurement();
        measurement.setTimestamp(1524746826400l);
        measurement.setUnit(Unit.degree);
        measurement.setValue(23.9);

        String result = JsonConverter.toJsonString(measurement);
        assertEquals("{\"unit\":\"degree\",\"timestamp\":1524746826400,\"value\":23.9}", result);
    }

    @Test
    public void toJsonByteResultTest() throws JsonProcessingException {
        byte[] expected = "{\"unit\":\"degree\",\"timestamp\":1524746826400,\"value\":23.9}".getBytes();

        Measurement measurement = new Measurement();
        measurement.setTimestamp(1524746826400l);
        measurement.setUnit(Unit.degree);
        measurement.setValue(23.9);

        byte[] result = JsonConverter.toJson(measurement);
        assertArrayEquals(expected, result);
    }

    @Test
    public void toJsonObjectTest() throws IOException {
        String json = "{\"unit\":\"degree\",\"timestamp\":1524746826400,\"value\":23.9}";

        Measurement measurement = JsonConverter.toJsonObject(json, Measurement.class);
        assertTrue(Unit.degree == measurement.getUnit());
        assertTrue(1524746826400l == measurement.getTimestamp());
        assertTrue(23.9 == measurement.getValue());
    }

    @Test(expected = IOException.class)
    public void toJsonObjectNotCorrectJsonTest() throws IOException {
        String json = "{\"unit\":gree\",\"timestamp\":1524746826400,\"value\":23.9}";

        Measurement measurement = JsonConverter.toJsonObject(json, Measurement.class);
    }

    @Test
    public void test() throws JsonProcessingException, NoSuchAlgorithmException {
        Measurement m = new Measurement();
        m.setValue(51);
        m.setUnit(Unit.percent);
        m.setTimestamp(1524754176432l);

        String s = JsonConverter.toJsonString(m);
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
        byte[] res = hasher.digest(s.getBytes());
        String result = new BigInteger(1, res).toString(16);
        System.out.println(result);
    }
}
