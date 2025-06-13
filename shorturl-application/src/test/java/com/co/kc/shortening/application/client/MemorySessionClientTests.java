package com.co.kc.shortening.application.client;

import com.co.kc.shortening.application.model.client.SessionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class MemorySessionClientTests {
    private SessionClient sessionClient;

    @BeforeEach
    public void initSessionClient() {
        sessionClient = new MemorySessionClient();
    }

    @Test
    void testGetSavedSession() {
        String sessionId = "testId";
        SessionDTO sessionDTO = new SessionDTO();
        sessionClient.save(sessionId, sessionDTO, 10L, TimeUnit.MINUTES);

        Assertions.assertNotNull(sessionClient.get(sessionId));
        Assertions.assertEquals(sessionDTO, sessionClient.get(sessionId));
    }

    @Test
    void testGetExpiredSession() {
        String sessionId = "testId";
        SessionDTO sessionDTO = new SessionDTO();
        sessionClient.save(sessionId, sessionDTO, 0L, TimeUnit.SECONDS);
        Assertions.assertNull(sessionClient.get(sessionId));
    }

    @Test
    void testGetRemovedSession() {
        String sessionId = "testId";
        SessionDTO sessionDTO = new SessionDTO();
        sessionClient.save(sessionId, sessionDTO, 10L, TimeUnit.MINUTES);
        Assertions.assertNotNull(sessionClient.get(sessionId));

        sessionClient.remove(sessionId);
        Assertions.assertNull(sessionClient.get(sessionId));
    }
}
