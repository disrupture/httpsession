package com.disrupture.httpsession;

import android.os.AsyncTask;

import java.util.Map;

class HttpRequestTask extends AsyncTask<Void, Void, HttpResponse> {
    private String requestMethod;
    private SafeUrl url;
    private Map<String, String> headers;
    private HttpResponseHandler responseHandler;

    HttpRequestTask(String requestMethod,
                    String baseUrl,
                    String path,
                    HttpResponseHandler responseHandler) {
        this(requestMethod, baseUrl, path, null, responseHandler);
    }

    HttpRequestTask(String requestMethod,
                    String baseUrl,
                    String path,
                    Map<String, String> headers,
                    HttpResponseHandler responseHandler) {
        this.requestMethod = requestMethod;
        this.url = new SafeUrl(baseUrl, path);
        this.headers = headers;
        this.responseHandler = responseHandler;
    }

    @Override
    protected HttpResponse doInBackground(Void... voids) {
        return HttpConnection.sendRequest(requestMethod, url, headers);
    }

    @Override
    protected void onPostExecute(HttpResponse httpResponse) {
        if (httpResponse == null) {
            responseHandler.onFailure(null);
        } else if (httpResponse.isSuccessful()) {
            responseHandler.onSuccess(httpResponse);
        } else {
            responseHandler.onFailure(httpResponse);
        }

        responseHandler.onFinish();
    }
}
