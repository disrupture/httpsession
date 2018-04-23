package com.disrupture.httpsession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionTest {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @Mock
    private HttpSession mockSession;

    @Before
    public void setUpMock() {
        // Need to mock calls that execute an asynctask because junit test can't handle that
        Mockito.doNothing()
            .when(mockSession)
            .get(Mockito.isA(String.class), Mockito.isA(HttpResponseHandler.class));
    }

    @Test
    public void emptyConstructor_exists() {
        HttpSession httpSession = new HttpSession();
        assertNull(httpSession.getBaseUrl());
    }

    @Test
    public void baseUrlConstructor_exists() {
        HttpSession httpSession = new HttpSession(BASE_URL);
        assertEquals(BASE_URL, httpSession.getBaseUrl());
    }

    @Test
    public void setBaseUrl() {
        HttpSession httpSession = new HttpSession();
        httpSession.setBaseUrl(BASE_URL);
        assertEquals(BASE_URL, httpSession.getBaseUrl());
    }

    /**
     * *********************************************************************************************
     *
     * The below tests only exists to catch any breaking api changes.
     *
     * *********************************************************************************************
     */

    @Test
    public void simpleGet() {
        mockSession.get("/posts", new HttpResponseHandler() {
            // no overrides required
        });
    }

    @Test
    public void simpleGet_onSuccessOverride() {
        mockSession.get("/posts", new HttpResponseHandler() {
            @Override
            public void onSuccess(HttpResponse response) {}
        });
    }

    @Test
    public void simpleGet_onFailureOverride() {
        mockSession.get("/posts", new HttpResponseHandler() {
            @Override
            public void onFailure(HttpResponse response) {}
        });
    }

    @Test
    public void simpleGet_onFinishOverride() {
        mockSession.get("/posts", new HttpResponseHandler() {
            @Override
            public void onFinish() {}
        });
    }

    @Test
    public void getWithHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        mockSession.get("/posts", headers, new HttpResponseHandler() {
            // no overrides required
        });
    }
}
