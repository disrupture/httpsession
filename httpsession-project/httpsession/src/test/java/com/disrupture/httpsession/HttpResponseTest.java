package com.disrupture.httpsession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class HttpResponseTest {
    @Test
    public void constructor_simpleCase() {
        int statusCode = 200;
        Map<String, List<String>> headers = new HashMap<>();
        List<String> contentTypeValue = new ArrayList<>();
        contentTypeValue.add("application/json");
        headers.put("Content-Type", contentTypeValue);
        String body = "test";

        HttpResponse httpResponse = new HttpResponse(statusCode, headers, body);

        assertEquals(statusCode, httpResponse.getStatusCode());
        assertEquals(1, httpResponse.getHeaders().size());
        assertEquals(contentTypeValue, httpResponse.getHeaders().get("Content-Type"));
        assertEquals(body, httpResponse.getBody());
    }

    @Test
    public void constructor_jsonObjectBody() throws JSONException {
        String jsonString = "{}";
        JSONObject jsonObject = new JSONObject(jsonString);
        Map<String, List<String>> headers = new HashMap<>();
        HttpResponse httpResponse = new HttpResponse(200, headers, jsonObject);
        assertTrue(httpResponse.getBody() instanceof JSONObject);
    }

    @Test
    public void constructor_jsonArrayBody() throws JSONException {
        String jsonString = "[]";
        JSONArray jsonArray = new JSONArray(jsonString);
        Map<String, List<String>> headers = new HashMap<>();
        HttpResponse httpResponse = new HttpResponse(200, headers, jsonArray);
        assertTrue(httpResponse.getBody() instanceof JSONArray);
    }

    @Test
    public void constructor_stringBody() {
        String bodyString = "test";
        Map<String, List<String>> headers = new HashMap<>();
        HttpResponse httpResponse = new HttpResponse(200, headers, bodyString);
        assertTrue(httpResponse.getBody() instanceof String);
        assertEquals(bodyString, httpResponse.getBody());
    }

    @Test
    public void constructor_booleanBody() {
        Map<String, List<String>> headers = new HashMap<>();
        HttpResponse httpResponse = new HttpResponse(200, headers, false);
        assertTrue(httpResponse.getBody() instanceof Boolean);
        assertEquals(false, httpResponse.getBody());
    }

    @Test
    public void constructor_integerBody() {
        int bodyInt = 8;
        Map<String, List<String>> headers = new HashMap<>();
        HttpResponse httpResponse = new HttpResponse(200, headers, bodyInt);
        assertTrue(httpResponse.getBody() instanceof Integer);
        assertEquals(bodyInt, httpResponse.getBody());
    }

    @Test
    public void constructor_longBody() {
        long bodyLong = 8L;
        Map<String, List<String>> headers = new HashMap<>();
        HttpResponse httpResponse = new HttpResponse(200, headers, bodyLong);
        assertTrue(httpResponse.getBody() instanceof Long);
        assertEquals(bodyLong, httpResponse.getBody());
    }

    @Test
    public void constructor_doubleBody() {
        double bodyDouble = 8.08;
        Map<String, List<String>> headers = new HashMap<>();
        HttpResponse httpResponse = new HttpResponse(200, headers, bodyDouble);
        assertTrue(httpResponse.getBody() instanceof Double);
        assertEquals(bodyDouble, httpResponse.getBody());
    }

    @Test
    public void constructor_nullBody() {
        Map<String, List<String>> headers = new HashMap<>();
        HttpResponse httpResponse = new HttpResponse(200, headers, null);
        assertEquals(null, httpResponse.getBody());
    }

    @Test
    public void isSuccessful_200_true() {
        HttpResponse httpResponse = new HttpResponse(200, null, null);
        assertTrue(httpResponse.isSuccessful());
    }

    @Test
    public void isSuccessful_400_false() {
        HttpResponse httpResponse = new HttpResponse(400, null, null);
        assertFalse(httpResponse.isSuccessful());
    }

    @Test
    public void isSuccessful_100_false() {
        HttpResponse httpResponse = new HttpResponse(100, null, null);
        assertFalse(httpResponse.isSuccessful());
    }

    @Test
    public void isSuccessful_199_false() {
        HttpResponse httpResponse = new HttpResponse(199, null, null);
        assertFalse(httpResponse.isSuccessful());
    }

    @Test
    public void isSuccessful_300_false() {
        HttpResponse httpResponse = new HttpResponse(300, null, null);
        assertFalse(httpResponse.isSuccessful());
    }

    @Test
    public void isSuccessful_299_true() {
        HttpResponse httpResponse = new HttpResponse(299, null, null);
        assertTrue(httpResponse.isSuccessful());
    }
}
