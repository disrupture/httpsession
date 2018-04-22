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

    public void get(String path, HttpResponseHandler responseHandler) {
        new HttpRequestTask("GET", baseUrl, path, responseHandler).execute();
    }

    // TODO: NEED A GET THAT ALSO ACCEPTS HEADERS AND JSON BODY
}
