package com.disrupture.httpsession;

import org.json.JSONObject;

import java.util.Map;

public class HttpResponseHandler {
    public void onSuccess(int statusCode) {}
    public void onSuccess(int statusCode, JSONObject response) {}
    public void onSuccess(int statusCode, Map<String, String> headers, JSONObject response) {}

    public void onFailure(int statusCode) {}
    public void onFailure(int statusCode, JSONObject response) {}
    public void onFailure(int statusCode, Map<String, String> headers, JSONObject response) {}

    public void onFinish() {}
}
