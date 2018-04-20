package com.disrupture.httpsession;

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
}
