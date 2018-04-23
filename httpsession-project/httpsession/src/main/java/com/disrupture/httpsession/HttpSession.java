package com.disrupture.httpsession;

import java.util.Map;

public class HttpSession {
    private String baseUrl;

    public HttpSession() {}

    public HttpSession(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void get(String path, HttpResponseHandler responseHandler) {
        new HttpRequestTask("GET", baseUrl, path, responseHandler).execute();
    }

    public void get(String path, Map<String, String> headers, HttpResponseHandler responseHandler) {
        new HttpRequestTask("GET", baseUrl, path, headers, responseHandler).execute();
    }
}
