package sg.dex.starfish.impl.remote;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import sg.dex.starfish.constant.Constant;
import sg.dex.starfish.exception.RemoteException;
import sg.dex.starfish.impl.AAsset;
import sg.dex.starfish.util.DID;
import sg.dex.starfish.util.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import static sg.dex.starfish.constant.Constant.*;

/**
 * This is an abstract class which have common code required
 * for RemoteAsset/RemoteBundle/RemoteOperation.
 * This class used to initialize the agent passed as an argument.
 */
public abstract class ARemoteAsset extends AAsset {

    protected RemoteAgent agent;

    protected ARemoteAsset(String meta, RemoteAgent agent) {
        super(meta);
        this.agent = agent;
    }

    @Override
    public DID getDID() {
        // DID of a remote asset is the DID of the appropriate agent with the asset ID as a resource path
        DID agentDID = agent.getDID();
        return agentDID.withPath(getAssetID());
    }

    @Override
    public InputStream getContentStream() {
        if (validateAssetType()) {
            URI uri = agent.getStorageURI(getAssetID());
            HttpGet httpget = new HttpGet(uri);
            try {
                CloseableHttpResponse response = agent.getHttpResponse(httpget);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    return HTTP.getContent(response);
                }
            } catch (IOException e) {
                throw new RemoteException("Error while getting the asset content for URI" + uri.toString(), e);
            }

        }
        throw new UnsupportedOperationException("Cannot get InputStream for asset of class: " + this.getClass().getCanonicalName());
    }

    /**
     * This method is added to support Orchestration
     *
     * @return
     */
    private boolean validateAssetType() {

        Object type = this.getMetadata().get(Constant.TYPE);

        return type.equals(DATA_SET)
                || (type.equals(OPERATION) &&
                this.getMetadata().get(Constant.CLASS).equals(ORCHESTRATION));
    }
}
