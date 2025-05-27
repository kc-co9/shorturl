package com.co.kc.shortening.application.client;

import com.co.kc.shortening.application.model.client.SessionDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class MemorySessionClientTests {
    private SessionClient sessionClient;

    @Before
    public void initSessionClient() {
        sessionClient = new MemorySessionClient();
    }

    @Test
    public void testGetSavedSession() {
        String sessionId = "testId";
        SessionDTO sessionDTO = new SessionDTO();
        sessionClient.save(sessionId, sessionDTO, 10L, TimeUnit.MINUTES);

        Assert.assertNotNull(sessionClient.get(sessionId));
        Assert.assertEquals(sessionDTO, sessionClient.get(sessionId));
    }

    @Test
    public void testGetExpiredSession() {
        String sessionId = "testId";
        SessionDTO sessionDTO = new SessionDTO();
        sessionClient.save(sessionId, sessionDTO, 0L, TimeUnit.SECONDS);
        Assert.assertNull(sessionClient.get(sessionId));
    }

    @Test
    public void testGetRemovedSession() {
        String sessionId = "testId";
        SessionDTO sessionDTO = new SessionDTO();
        sessionClient.save(sessionId, sessionDTO, 10L, TimeUnit.MINUTES);
        Assert.assertNotNull(sessionClient.get(sessionId));

        sessionClient.remove(sessionId);
        Assert.assertNull(sessionClient.get(sessionId));
    }
}
