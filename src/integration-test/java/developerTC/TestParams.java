package developerTC;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import sg.dex.starfish.Asset;
import sg.dex.starfish.impl.memory.MemoryAsset;
import sg.dex.starfish.impl.remote.RemoteAccount;
import sg.dex.starfish.impl.remote.RemoteAgent;
import sg.dex.starfish.impl.remote.RemoteDataAsset;
import sg.dex.starfish.util.Params;

import java.io.IOException;
import java.net.URISyntaxException;

public class TestParams {

    @Test
    public void testAssetByDID() throws IOException, URISyntaxException {
        RemoteAccount remoteAccount = RemoteAccount.create("Aladdin", "OpenSesame");
        RemoteAgent remoteAgent = RemoteAgent.connect("http://52.230.82.125:3030", remoteAccount);
        Asset asset = MemoryAsset.create("test".getBytes());
        RemoteDataAsset remoteDataAsset = remoteAgent.uploadAsset(asset);

        Asset asset1 = Params.getAssetByDID(remoteDataAsset.getDID(), remoteAccount);
        Assert.assertEquals(asset.getMetadataString(), asset1.getMetadataString());
        Assert.assertEquals(asset.getAssetID(), asset1.getAssetID());
    }
}
