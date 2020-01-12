package at.ac.tuwien.infosys.iotclient.utils;

import org.junit.Before;
import org.junit.Test;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BlockHistoryReplayerTest {

    private Queue<EthBlock.Block> blocks;

    @Before
    public void setUp(){
        EthBlock.Block block1 = new EthBlock.Block();
        block1.setHash("0x1");
        block1.setParentHash("0x0");
        block1.setNumber("0x1");

        EthBlock.Block block2 = new EthBlock.Block();
        block2.setHash("0x2");
        block2.setParentHash("0x1");
        block2.setNumber("0x2");

        EthBlock.Block block3 = new EthBlock.Block();
        block3.setHash("0x3");
        block3.setParentHash("0x2");
        block3.setNumber("0x3");

        EthBlock.Block block4 = new EthBlock.Block();
        block4.setHash("0x4");
        block4.setParentHash("0x3");
        block4.setNumber("0x4");

        blocks = new ConcurrentLinkedQueue<>();
        blocks.add(block1);
        blocks.add(block2);
        blocks.add(block3);
        blocks.add(block4);
    }

    @Test
    public void checkBlocksSuccessTest(){

        BlockHistoryReplayer replayer = new BlockHistoryReplayer();
        replayer.ckeckBlocks(blocks);
        //no check needed because it shall pass without any further actions.
    }

    @Test(expected = IllegalStateException.class)
    public void checkBlocksMultipleParents(){
        EthBlock.Block block5 = new EthBlock.Block();
        block5.setHash("0x2");
        block5.setParentHash("0x4");
        block5.setNumber("0x5");

        blocks.add(block5);

        BlockHistoryReplayer replayer = new BlockHistoryReplayer();
        replayer.ckeckBlocks(blocks);
    }

    @Test
    public void checkBlocksLeftOutBlockTest(){
        EthBlock.Block block5 = new EthBlock.Block();
        block5.setHash("0x10");
        block5.setParentHash("0x4");
        block5.setNumber("0x5");

        Queue<EthBlock.Block> newBlocks = new ConcurrentLinkedQueue<>();
        newBlocks.add(block5);
        newBlocks.addAll(blocks);

        BlockHistoryReplayer replayer = new BlockHistoryReplayer();
        replayer.ckeckBlocks(newBlocks);

        //a warn logging event is expected
    }
}
