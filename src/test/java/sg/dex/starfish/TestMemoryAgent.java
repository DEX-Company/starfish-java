package sg.dex.starfish;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import sg.dex.crypto.Hex;
import sg.dex.starfish.impl.MemoryAgent;
import sg.dex.starfish.impl.MemoryAsset;

public class TestMemoryAgent {
	private static final byte[] BYTE_DATA = Hex.toBytes("0123456789");

	@Test 
	public void testAgentID() {
		DID did=DID.parse(Utils.createRandomDIDString());
		MemoryAgent ma=MemoryAgent.create(did);
		assertEquals(did,ma.getDID());
	}
	
	@Test public void testTransfer() {
		MemoryAgent agent1=MemoryAgent.create();
		MemoryAgent agent2=MemoryAgent.create();
		
		MemoryAsset a=MemoryAsset.create(BYTE_DATA);
		String id=a.getAssetID();
		assertNull(agent1.getAsset(id));
		
		Asset a1=agent1.uploadAsset(a);
		assertEquals(a1,agent1.getAsset(id));
		
		Asset a2=agent2.uploadAsset(a1);
		assertNotNull(agent2.getAsset(id));
		
		assertEquals(a1.getMetadataString(),a2.getMetadataString());
	}
}
