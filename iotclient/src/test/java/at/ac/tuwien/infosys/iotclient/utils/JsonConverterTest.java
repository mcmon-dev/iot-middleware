package at.ac.tuwien.infosys.iotclient.utils;

import at.ac.tuwien.infosys.iotclient.model.Message;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JsonConverterTest {

    @Test
    public void toObjectTest() throws IOException {

        byte[] json = "{\"id\":\"coap://127.0.0.1:60001/temperature\",\"value\":26.0,\"timestamp\":1525879019843,\"unit\":\"degree\",\"type\":\"Temperature\"}".getBytes();
        Message msg = JsonConverter.toJsonObject(json, Message.class);

        assertEquals("coap://127.0.0.1:60001/temperature", msg.getId());
    }
}
