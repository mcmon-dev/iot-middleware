package at.ac.tuwien.infosys.iotclient.utils;


import at.ac.tuwien.infosys.iotclient.model.CmdOptions;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArgumentHandlerTest {



    @Test
    public void parseSuccessfulLongArgsTest(){
        String[] args = {"-filename", "result.csv", "-duration", "20000"};

        ArgumentHandler handler = new ArgumentHandler();
        CmdOptions options = handler.parse(args);
        assertEquals("result.csv", options.getFileName());
        assertTrue(20000l == options.getDuration());
    }

    @Test
    public void parseSuccessfulShortArgsTest(){
        String[] args = {"-f", "result.csv", "-d", "20000"};

        ArgumentHandler handler = new ArgumentHandler();
        CmdOptions options = handler.parse(args);
        assertEquals("result.csv", options.getFileName());
        assertTrue(20000l == options.getDuration());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseMissingArgTest(){
        String[] args = {"-duration", "20000"};

        ArgumentHandler handler = new ArgumentHandler();
        CmdOptions options = handler.parse(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseNoNumberTest(){
        String[] args = {"-filename", "result.csv", "-duration", "NoNumber"};

        ArgumentHandler handler = new ArgumentHandler();
        CmdOptions options = handler.parse(args);
    }


    @Test
    public void parseWithOptionalOptionsTest(){
        String[] args = {"-f", "result.csv", "-d", "20000", "-t", "3", "-i", "192.168.0.100"};

        ArgumentHandler handler = new ArgumentHandler();
        CmdOptions options = handler.parse(args);
        assertEquals("result.csv", options.getFileName());
        assertTrue(20000l == options.getDuration());
        assertEquals("192.168.0.100", options.getId());
        assertTrue(3 == options.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseWithOptionalOptionsNotNumberArgTest(){
        String[] args = {"-f", "result.csv", "-d", "20000", "-t", "noNumber", "-i", "192.168.0.100"};

        ArgumentHandler handler = new ArgumentHandler();
        CmdOptions options = handler.parse(args);
    }
}
