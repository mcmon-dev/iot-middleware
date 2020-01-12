package at.ac.tuwien.infosys.iotclient;

import at.ac.tuwien.infosys.iotclient.model.*;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogWriterTest {

    @Test
    @Ignore
    public void writeLogsToFileTest(){

        Message msg = new Message("coap://127.0.0.1:60001/temperature", 23.0, 1525775067162l, Unit.degree, MeasureType.Temperature);
        RealtimeLog rtLog = new RealtimeLog(msg, 1525775121720l);
        //IntegrityLog intLog = new IntegrityLog(msg, 1525775270926l, 1525775283622l, BigInteger.valueOf(100030));

        Queue<RealtimeLog> realtimeLogs = new ConcurrentLinkedQueue<>();
        Queue<IntegrityLog> integrityLogs = new ConcurrentLinkedQueue<>();
        realtimeLogs.add(rtLog);
      //  integrityLogs.add(intLog);

      // LogWriter.writeLogsToFile("config1_results.csv", realtimeLogs, integrityLogs);

    }
}
