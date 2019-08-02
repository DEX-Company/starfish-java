package sg.dex.crypto;

import org.junit.Test;
import sg.dex.starfish.constant.Constant;
import sg.dex.starfish.util.Hex;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("javadoc")
public class TestSHA3 {


    @Test
    public void testEmptySHA3() {
        // Empty byte array
        byte[] h1 = new ComputeHashFactory().getHashfunction(Constant.SHA3_256).compute(new byte[]{});
        assertEquals("a7ffc6f8bf1ed76651c14756a061d662f580ff4de43b49fa82d80a4b80f8434a", Hex.toString(h1));
    }

    @Test
    public void testBasicSHA3() {
        // test vectors from https://www.di-mgt.com.au/sha_testvectors.html

        byte[] h1 = new ComputeHashFactory().getHashfunction(Constant.SHA3_256).compute("abc");
        assertEquals("3a985da74fe225b2045c172d6bd390bd855f086e3e9d525b46bfe24511431532", Hex.toString(h1));

        byte[] h2 = new ComputeHashFactory().getHashfunction(Constant.SHA3_256).compute("abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq");
        assertEquals("41c0dba2a9d6240849100376a8235e2c82e1b9998a999e21db32dd97496d3376", Hex.toString(h2));
    }

}
