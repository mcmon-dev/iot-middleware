package at.ac.tuwien.infosys.iotclient.utils;

import at.ac.tuwien.infosys.iotclient.model.IntegrityLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionCommitChecker {

    private static final Logger logger = LoggerFactory.getLogger(BlockHistoryReplayer.class);

    private final BigInteger COMMIT_AMOUNT = BigInteger.valueOf(11);


    public void checkCommits(Queue<IntegrityLog> logs, Map<EthBlock.Block, Long> recordedBlocks) {

        Set<EthBlock.Block> blocks = recordedBlocks.keySet();

        for (IntegrityLog log : logs) {
            if (!log.isRemoved()) {
                BigInteger commitNum = log.getBlockNumber().add(COMMIT_AMOUNT);
                List<EthBlock.Block> commitBlocks = blocks.stream().filter(b -> b.getNumber().equals(commitNum)).collect(Collectors.toList());

                long tsTxBlock = getTimestamp(log.getBlockHash(), recordedBlocks);
                log.setBlockTs(tsTxBlock);

                if (commitBlocks.size() == 0) {
                    logger.info("Log not commited at the moment. Message-Id: {} TxHash: {}", log.getMessage().getId(), log.getTransactionHash());
                } else if (commitBlocks.size() > 1) {
                    logger.info("Found several blocks.");
                    EthBlock.Block block = commitBlocks.stream().max(Comparator.comparing(EthBlock.Block::getTotalDifficulty)).get();
                    log.setCommitTs(recordedBlocks.get(block));
                } else {
                    log.setCommitTs(recordedBlocks.get(commitBlocks.get(0)));
                }
            }
        }
    }

    private long getTimestamp(String blockHash, Map<EthBlock.Block, Long> recordedBlocks){

        long ts = 0;

        Set<EthBlock.Block> blocks = recordedBlocks.keySet();
        List<EthBlock.Block> txBlocks = blocks.stream().filter(b -> b.getHash().equals(blockHash)).collect(Collectors.toList());


        if(txBlocks.size() != 1){
            logger.warn("The number of found blocks for the block hash {} is not 1.", blockHash);
        } else {
            EthBlock.Block txBlock = txBlocks.get(0);
            ts = recordedBlocks.get(txBlock);
        }

        return ts;
    }
}
