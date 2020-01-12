package at.ac.tuwien.infosys.iotclient.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.ipc.UnixIpcService;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;


public class BlockHistoryReplayer {

    private static final Logger logger = LoggerFactory.getLogger(BlockHistoryReplayer.class);
    private Set<String> blockHashes;

    /**
     * Retrieves blocks from the main chain.
     *
     * @param firstBlock     first blocknumber where the retrieval shall start.
     * @param recordedBlocks blocks and according timestamp which were recorded by a block observable.
     * @return map which has blocknumbers as key and timestamp when block was received as value. Contains only timestamps from blocks of the main chain.
     */
    public Map<BigInteger, Long> getMainChainBlocks(BigInteger firstBlock, Map<Block, Long> recordedBlocks) {
        Map<BigInteger, Long> result = new HashMap<>();
        Queue<Block> blocks = new ConcurrentLinkedQueue<>();
        blockHashes = new HashSet<>();


        Web3j web3 = Web3j.build(new UnixIpcService(Defines.IPC_PATH));
        web3.replayBlocksObservable(DefaultBlockParameter.valueOf(firstBlock), DefaultBlockParameterName.LATEST, false).subscribe(block -> {
            blocks.add(block.getBlock());
        });

        int size = -1;
        try {
            while (blocks.size() > size) {
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
           logger.error("Error occurred while waiting for replay blocks to top.");
        }

        web3.shutdown();

        for(Block block : blocks){

            Long timestamp = recordedBlocks.get(block);

            if (timestamp == null){
                logger.warn("Block with number {} and hash {} is not recorded.", block.getNumber(), block.getHash());
            } else {
                result.put(block.getNumber(), timestamp);
                blockHashes.add(block.getHash());
                logger.info("Added blocknumber {} with ts {}.", block.getNumber(), timestamp);
            }
        }

        logger.warn("{} of the recorded block are discarded.", (recordedBlocks.size() - result.size()));

        ckeckBlocks(blocks);

        return result;
    }

    public void ckeckBlocks(Queue<Block> blocks){

        Block block = (Block) blocks.toArray()[blocks.size()-1];

        boolean keepGoing = true;
        int runs = 0;

        while(keepGoing) {
            String hash = block.getParentHash();
            List<Block> parentBlocks = blocks.stream().filter(b -> b.getHash().equals(hash)).collect(Collectors.toList());

            if(parentBlocks == null || parentBlocks.size() == 0){
                logger.info("No more blocks are found. Last blocknumber is {}. Hash: {}", block.getNumber(), block.getHash());
                keepGoing = false;
            }else if(parentBlocks.size() > 1){
                logger.error("FATAL ERROR: more than one parent block is found. {}", parentBlocks);
                throw new IllegalStateException("There are more than one parent in the recorded block list");
            } else{
                block = parentBlocks.get(0);
            }

            runs++;
        }

        if(runs == blocks.size()){
            logger.info("There is no conflict on the recorded blocks. Amount of blocks: {} Runs: {}", blocks.size(), runs);
        } else {
            logger.warn("The amount of blocks {} is not the checked parent instances {}", blocks.size(), runs);
        }
    }

    public Set<String> getBlockHashes() {
        return blockHashes;
    }
}
