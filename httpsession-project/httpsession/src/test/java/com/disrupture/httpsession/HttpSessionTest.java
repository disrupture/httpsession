package com.disrupture.httpsession;

import org.json.JSONObject;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class HttpSessionTest {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @Test
    public void emptyConstructor_exists() {
        @SuppressWarnings("unused")
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

    // TODO: TEST TO MAKE SURE / CAN BE ON THE END OF THE BASEURL AND THE START OF THE PATH

    @Test
    public void simpleGet() {
        HttpSession httpSession = new HttpSession(BASE_URL);
        httpSession.get("/posts", new HttpResponseHandler() {
            // no overrides required
        });
    }

    // TODO: DECIDE IF ALL CALLBACKS SHOULD BE HIT FOR SUCCESS AND WRITE UNIT TEST FOR THAT
    @Test
    public void simpleGet_onSuccessOverrides() {
        HttpSession httpSession = new HttpSession(BASE_URL);
        httpSession.get("/posts", new HttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode) {
                super.onSuccess(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                super.onSuccess(statusCode, response);
            }

            @Override
            public void onSuccess(int statusCode, Map<String, String> headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }
        });
    }

    @Test
    public void simpleGet_onFailureOverrides() {
        HttpSession httpSession = new HttpSession(BASE_URL);
        httpSession.get("/posts", new HttpResponseHandler() {
            @Override
            public void onFailure(int statusCode) {
                super.onFailure(statusCode);
            }

            @Override
            public void onFailure(int statusCode, JSONObject response) {
                super.onFailure(statusCode, response);
            }

            @Override
            public void onFailure(int statusCode, Map<String, String> headers, JSONObject response) {
                super.onFailure(statusCode, headers, response);
            }
        });
    }

    @Test
    public void simpleGet_onFinishOverride() {

        HttpSession httpSession = new HttpSession(BASE_URL);
        httpSession.get("/posts", new HttpResponseHandler() {
            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }
}
