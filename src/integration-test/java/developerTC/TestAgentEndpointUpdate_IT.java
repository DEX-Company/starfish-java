package developerTC;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sg.dex.starfish.Resolver;
import sg.dex.starfish.dexchain.DexResolver;
import sg.dex.starfish.impl.remote.RemoteAgent;
import sg.dex.starfish.util.DID;
import sg.dex.starfish.util.JSON;
import sg.dex.starfish.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * As a developer managing a Network Agent,
 * I need to be able to update service endpoints for my Agent
 */
public class TestAgentEndpointUpdate_IT {

    @BeforeAll
    @DisplayName("Check if RemoteAgent is up!!")
    public static void init() {
        Assumptions.assumeTrue(ConnectionStatus.checkAgentStatus(), "Agent :" + AgentService.getSurferUrl() + "is not running. is down");
    }


    @Test
    public void testServiceEndPoint() throws IOException {
        // creating an instance of Remote Agent
        String surferURL = AgentService.getSurferUrl();
        RemoteAgent remoteAgent = createRemoteAgent(surferURL);

        assertTrue(remoteAgent.getStorageEndpoint().contains("/api/v1/assets"));
        assertTrue(remoteAgent.getMetaEndpoint().contains("/api/v1/meta"));

        // below endpoint wll be null and it was not initialize while creating services for Remote Agent
        assertNull(remoteAgent.getInvokeEndpoint());
        assertNull(remoteAgent.getAuthEndpoint());
    }

    private RemoteAgent createRemoteAgent(String host) {

        Map<String, Object> ddo = new HashMap<>();
        List<Map<String, Object>> services = new ArrayList<>();
        // add the respective end points
        services.add(Utils.mapOf(
                "type", "DEP.Meta.v1",
                "serviceEndpoint", host + "/api/v1/meta"));
        services.add(Utils.mapOf(
                "type", "DEP.Storage.v1",
                "serviceEndpoint", host + "/api/v1/assets"));

        // adding to ddo map
        ddo.put("service", services);
        // converting ddo to string
        String ddoString = JSON.toPrettyString(ddo);

        Resolver resolver = DexResolver.create();
        // creating unique DID
        DID surferDID = DID.createRandom();

        // registering the DID and DDO
        resolver.registerDID(surferDID, ddoString);
        // creating a Remote agent instance for given Ocean and DID
        return RemoteAgent.connect(resolver, surferDID, AgentService.getRemoteAccount());

    }
}
