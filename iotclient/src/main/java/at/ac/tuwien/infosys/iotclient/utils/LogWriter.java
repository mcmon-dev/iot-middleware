package at.ac.tuwien.infosys.iotclient.utils;


import at.ac.tuwien.infosys.iotclient.model.IntegrityLog;
import at.ac.tuwien.infosys.iotclient.model.RealtimeLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;
import java.util.Queue;

public class LogWriter {

    private static final Logger logger = LoggerFactory.getLogger(LogWriter.class);

    private static final char COMMA_DELIMITER = ',';
    private static final char NEW_LINE_SEPARATOR = '\n';
    private static final String SUFFIX = ".csv";

    private static final String FILE_HEADER = "id,ts_sent,rt:ts_rec,rt:delay,int:ts_rec_hash,int:ts_rec_file,int:delay_hf,int:delay,int:ts_tx_block,int:ts_commit,int:status,int:tx_hash";
    private static final String FILE_HEADER_BLOCKS = "blockNubmer,timestamp";

    public static void writeLogsToFile(String fileName, Queue<RealtimeLog> rtLogs, Queue<IntegrityLog> intLogs) {

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName+SUFFIX);
            fileWriter.append(FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (RealtimeLog log : rtLogs) {
                IntegrityLog iLog = findIntegrityLog(log, intLogs);
                fileWriter.append(log.getMessage().getId()).append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(log.getMessage().getTimestamp())).append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(log.getTsReceived())).append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(log.getDelay())).append(COMMA_DELIMITER);

                if(iLog == null){
                    fileWriter.append("MISSING,,,,,");
                }else {
                    fileWriter.append(String.valueOf(iLog.getTsHash())).append(COMMA_DELIMITER);
                    fileWriter.append(String.valueOf(iLog.getTsFile())).append(COMMA_DELIMITER);
                    fileWriter.append(String.valueOf(iLog.getHashFileDelay())).append(COMMA_DELIMITER);
                    fileWriter.append(String.valueOf(iLog.getDelay())).append(COMMA_DELIMITER);
                    fileWriter.append(String.valueOf(iLog.getBlockTs())).append(COMMA_DELIMITER);
                    fileWriter.append(String.valueOf(iLog.getCommitTs())).append(COMMA_DELIMITER);
                    String status = iLog.isRemoved() ? "Removed" : "Valid";
                    fileWriter.append(status).append(COMMA_DELIMITER);
                    fileWriter.append(iLog.getTransactionHash());
                }

                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            logger.warn("{} integrity logs without corresponding real-time log remain.", intLogs);

            for(IntegrityLog log : intLogs){
                fileWriter.append(log.getMessage().getId()).append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(log.getMessage().getTimestamp())).append(COMMA_DELIMITER);
                fileWriter.append("REAL-TIME MISSING").append(COMMA_DELIMITER);
                fileWriter.append("REAL-TIME MISSING").append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(log.getTsHash())).append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(log.getTsFile())).append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(log.getHashFileDelay())).append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(log.getDelay())).append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(log.getBlockTs())).append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(log.getCommitTs())).append(COMMA_DELIMITER);
                String status = log.isRemoved() ? "Removed" : "Valid";
                fileWriter.append(status).append(COMMA_DELIMITER);
                fileWriter.append(log.getTransactionHash());

                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            logger.info("Finished writing time results into file {}{}.", fileName, SUFFIX);

        } catch (IOException e) {
            logger.error("", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    logger.error("Error during finally block.", e);
                }
            }
        }
    }

    public static void writeBlocksToFile(String fileName, Map<BigInteger, Long> blocks){

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName+SUFFIX);
            fileWriter.append(FILE_HEADER_BLOCKS);
            fileWriter.append(NEW_LINE_SEPARATOR);

            for(Map.Entry<BigInteger, Long> entry : blocks.entrySet()){
                fileWriter.append(String.valueOf(entry.getKey())).append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(entry.getValue()));
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            logger.info("Finished writing blocks into file {}.{}.", fileName, SUFFIX);

        } catch (IOException e) {
            logger.error("", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    logger.error("Error during finally block.", e);
                }
            }
        }

    }

    public static IntegrityLog findIntegrityLog(RealtimeLog log, Queue<IntegrityLog> intLogs) {

        IntegrityLog result = null;

        for (IntegrityLog iLog : intLogs) {

            if (iLog.isSameMessage(log.getMessage())) {
                intLogs.remove(iLog);
                result = iLog;
            }
        }

        return result;
    }
}
