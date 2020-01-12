package at.ac.tuwien.infosys.iotclient;

import at.ac.tuwien.infosys.iotclient.model.Message;
import at.ac.tuwien.infosys.iotclient.utils.Defines;
import at.ac.tuwien.infosys.iotclient.utils.JsonConverter;
import com.sun.org.apache.xpath.internal.operations.Mult;
import io.ipfs.api.IPFS;
import io.ipfs.multibase.Base58;
import io.ipfs.multihash.Multihash;
import org.junit.Test;
import org.web3j.abi.datatypes.generated.Bytes32;

import javax.swing.plaf.multi.MultiMenuItemUI;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class IpfsTest{

    @Test
    public void testIpfsRec() throws IOException {
        Multihash.Type sha216 = Multihash.Type.lookup(18);
        System.out.println(sha216);

       // IPFS ipfs = new IPFS(Defines.IPFS_ADDRESS);
        Multihash filePointer = Multihash.fromBase58("QmRKvr81cQRodBJuh1Y4NQyFx9i26R3uVMFH7e3sstnkSn");
        byte[] h = filePointer.toBytes();
        System.out.println(Arrays.toString(h));
        System.out.println(filePointer.toHex());

        Multihash tst = new Multihash(Multihash.Type.sha2_256, new byte[]{44, 99, -70, 60, 35, 25, -81, -84, -117, 45, 121, -16, -103, 58, -34, -85, 121, 50, -111, 63, 42, 57, 21, -1, -16, -105, -15, 35, -108, -67, 69, -37});
        System.out.println("############### "+tst);

        byte[] b = "QmRKvr81cQRodBJuh1Y4NQyFx9i26R3uVMFH7e3sstnkSn".getBytes();
        //Multihash.fromHex("516d817140c0d54db3f74af44070e033b932917789d1e08ef23661b4d141d83e6b8a");
        System.out.println(Arrays.toString(b));
        System.out.println(b.length);

        byte[] full = new byte[]{56, 49, 55, 49, 52, 48, 99, 48, 100, 53, 52, 100, 98, 51, 102, 55, 52, 97, 102, 52, 52, 48, 55, 48, 101, 48, 51, 51, 98, 57, 51, 50, 57, 49, 55, 55, 56, 57, 100, 49, 101, 48, 56, 101, 102, 50, 51, 54, 54, 49, 98, 52, 100, 49, 52, 49, 100, 56, 51, 101, 54, 98, 56, 97};

        System.out.println("Correct Hex file Pointer: "+filePointer.toHex()+ " Length: "+filePointer.toHex().length());
        System.out.println(Base58.encode(full));


        Multihash.fromHex("12202c63ba3c2319afac8b2d79f0993adeab7932913f2a3915fff097f12394bd45db");
        Multihash hope =  Multihash.fromHex("1220817140c0d54db3f74af44070e033b932917789d1e08ef23661b4d141d83e6b8a");
        System.out.println(hope);
        //filePointer = Multihash.fromHex("817140c0d54db3f74af44070e033b932917789d1e08ef23661b4d141d83e6b8a");
        int index = Multihash.Type.sha2_256.index;
        int size = Multihash.Type.sha2_256.length;
        System.out.println("Index: "+index+" Size: "+size);
        System.out.println("Index: "+(byte)index+" Size: "+(byte) size);

        String enc = Base58.encode(new byte[]{18, 32});
        System.out.println(Arrays.toString(Base58.decode("Qm")));

        /*byte[] fileContents = ipfs.cat(filePointer);

        Message message = JsonConverter.toJsonObject(fileContents, Message.class);
        System.out.println(message);
        System.out.println(JsonConverter.toJsonString(message));*/

    }

    @Test
    public void test(){
        BigInteger i = BigInteger.valueOf(33);
        String s = i.toString();

        System.out.println(i);
    }


}