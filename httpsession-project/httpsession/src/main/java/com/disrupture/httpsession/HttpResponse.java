package com.disrupture.httpsession;

import java.util.List;
import java.util.Map;

public class HttpResponse {
    private int statusCode;
    private Map<String, List<String>> headers;
    private Object body;

    HttpResponse(int statusCode, Map<String, List<String>> headers, Object body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    /**
     * The HTTP status code of the response.
     *
     * @return status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * The HTTP response headers from the response.
     *
     * @return headers
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * The body of the response.
     *
     * @return a JSONObject, JSONArray, String, Boolean, Integer, Long, Double, or NULL
     */
    public Object getBody() {
        return body;
    }
}
