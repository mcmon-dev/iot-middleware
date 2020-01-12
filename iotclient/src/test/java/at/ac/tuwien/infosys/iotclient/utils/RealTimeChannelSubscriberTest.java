package at.ac.tuwien.infosys.iotclient.utils;


import at.ac.tuwien.infosys.iotclient.subscriber.RealtimeChannelSubsciber;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;

public class RealTimeChannelSubscriberTest {

    @Test
    public void subscribeStringAmountTest(){

        RealtimeChannelSubsciber subsciber = Mockito.mock(RealtimeChannelSubsciber.class);
        Mockito.doCallRealMethod().when(subsciber).subscribe(Mockito.anyString(), Mockito.anyInt());

        ArgumentCaptor<String[]> valueCapture = ArgumentCaptor.forClass(String[].class);
        doNothing().when(subsciber).subscribe(valueCapture.capture());

        subsciber.subscribe("192.168.0.100:60001", 3);

        String[] expectedResults = { "coap://192.168.0.100:60001/0",  "coap://192.168.0.100:60001/1",  "coap://192.168.0.100:60001/2"};

        assertTrue(expectedResults.length == valueCapture.getValue().length);

        for(int i = 0; i < expectedResults.length; i++){
            assertEquals(expectedResults[i], valueCapture.getValue()[i]);
        }
    }
}
