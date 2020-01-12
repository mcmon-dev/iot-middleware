package at.ac.tuwien.infosys.iotclient.utils;

import at.ac.tuwien.infosys.iotclient.model.*;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LogWriterTest {

    @Test
    public void findIntegrityLogRemoveTest(){


        Message msg1 = new Message("msg1", 999, 1, Unit.degree, MeasureType.Temperature);

        IntegrityLog log1 = new IntegrityLog("hash1", new Message(), 1, 3, BigInteger.valueOf(20), "blockhash1", false);
        IntegrityLog log2 = new IntegrityLog("hash2", new Message(), 1, 3, BigInteger.valueOf(20), "blockhash2", false);
        IntegrityLog log3 = new IntegrityLog("hash3", msg1, 1, 3, BigInteger.valueOf(20), "blockhash3", false);
        IntegrityLog log4 = new IntegrityLog("hash4", new Message(), 1, 3, BigInteger.valueOf(20), "blockhash4", false);
        IntegrityLog log5 = new IntegrityLog("hash5", new Message(), 1, 3, BigInteger.valueOf(20), "blockhash5", false);


        Queue<IntegrityLog> integrityLogs = new ConcurrentLinkedQueue<>();
        integrityLogs.addAll(Arrays.asList(log1,log2,log3,log4,log5));

        RealtimeLog realtimeLog = new RealtimeLog(msg1, 1);
        IntegrityLog result =  LogWriter.findIntegrityLog(realtimeLog, integrityLogs);
        assertEquals("hash3", result.getTransactionHash());
        assertTrue(4 == integrityLogs.size());
        assertFalse(integrityLogs.contains(log3));

    }

    @Test
    public void findIntegrityLogRemovedTxTest(){

        Message msg1 = new Message("msg1", 999, 1, Unit.degree, MeasureType.Temperature);

        IntegrityLog log1 = new IntegrityLog("hash1", new Message(), 1, 3, BigInteger.valueOf(20), "blockhash1", false);
        IntegrityLog log2 = new IntegrityLog("hash2", msg1, 1, 2, BigInteger.valueOf(20), "blockhash2", false);
        IntegrityLog log3 = new IntegrityLog("hash3", new Message(), 1, 3, BigInteger.valueOf(20), "blockhash3", false);
        IntegrityLog log4 = new IntegrityLog("hash2", msg1, 2, 3, BigInteger.valueOf(20), "blockhash4", true);
        IntegrityLog log5 = new IntegrityLog("hash2", msg1, 3, 4, BigInteger.valueOf(20), "blockhash5", false);
        IntegrityLog log6 = new IntegrityLog("hash6", new Message(), 1, 3, BigInteger.valueOf(20), "blockhash6", false);


        Queue<IntegrityLog> integrityLogs = new ConcurrentLinkedQueue<>();
        integrityLogs.addAll(Arrays.asList(log1,log2,log3,log4,log5, log6));

        RealtimeLog realtimeLog = new RealtimeLog(msg1, 1);
        IntegrityLog result =  LogWriter.findIntegrityLog(realtimeLog, integrityLogs);
        assertEquals("hash2", result.getTransactionHash());
        assertTrue(3 == result.getTsHash());
        assertFalse(result.isRemoved());

    }

    @Test
    public void findIntegrityLogRemovedTxNoNewTxAfterRemoveTest(){

        Message msg1 = new Message("msg1", 999, 1, Unit.degree, MeasureType.Temperature);

        IntegrityLog log1 = new IntegrityLog("hash1", new Message(), 1, 3, BigInteger.valueOf(20), "blockhash1", false);
        IntegrityLog log2 = new IntegrityLog("hash2", msg1, 1, 2, BigInteger.valueOf(20), "blockhash2", false);
        IntegrityLog log3 = new IntegrityLog("hash3", new Message(), 1, 3, BigInteger.valueOf(20), "blockhash3", false);
        IntegrityLog log4 = new IntegrityLog("hash2", msg1, 2, 3, BigInteger.valueOf(20), "blockhash4", true);
        IntegrityLog log6 = new IntegrityLog("hash6", new Message(), 1, 3, BigInteger.valueOf(20), "blockhash6", false);


        Queue<IntegrityLog> integrityLogs = new ConcurrentLinkedQueue<>();
        integrityLogs.addAll(Arrays.asList(log1,log2,log3,log4,log6));

        RealtimeLog realtimeLog = new RealtimeLog(msg1, 1);
        IntegrityLog result =  LogWriter.findIntegrityLog(realtimeLog, integrityLogs);
        assertEquals("hash2", result.getTransactionHash());
        assertTrue(2 == result.getTsHash());
        assertTrue(result.isRemoved());

    }
}
