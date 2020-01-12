package at.ac.tuwien.infosys.iotclient.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonConverter {

    private static final Logger logger = LoggerFactory.getLogger(JsonConverter.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Converts given object into json byte array.
     * @param object
     * @return null if an error occured.
     */
    public static byte[] toJson(Object object) throws JsonProcessingException {
        byte[] payload = null;

        return  payload = mapper.writeValueAsBytes(object);
    }

    /**
     * Converts Object into json string.
     * @param object
     * @return null if an error occured.
     */
    public static String toJsonString(Object object) throws JsonProcessingException {
        byte[] payload = toJson(object);
        String result = null;

        if(payload != null){
            result = new String(payload);
        }
        return result;
    }

    /**
     * Converts json string to an object of the given class.
     * @param json
     * @param clazz
     * @param <T>
     * @return null if error occured.
     */
    public static <T> T toJsonObject(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public static <T> T toJsonObject(byte[] json, Class<T> clazz) throws IOException {
        logger.debug("{}", json);
        return mapper.readValue(json, clazz);
    }
}
