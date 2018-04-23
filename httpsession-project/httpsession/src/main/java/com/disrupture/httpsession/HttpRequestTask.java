package com.disrupture.httpsession;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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

        // TODO: ABSTRACT LOW LEVEL HTTP CODE OUT SO CAN BE USED IN SYNCHRONOUS CONTEXT AS WELL
        // TODO: HOW WOULD A 300 REDIRECT BE HANDLED

        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        BufferedReader bufferedReader;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            httpURLConnection = (HttpURLConnection) url.getUrl().openConnection();
            httpURLConnection.setRequestMethod(requestMethod);
            // Add request headers
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpURLConnection.setRequestProperty(header.getKey(), header.getValue());
                }
            }

            // TODO: IF PASSING A BODY ALONG THEN NEED TO SETDOOUTPUT TO TRUE

            // Explicit call to open communications link (network traffic occurs here).
            // This line isn't technically necessary as any operation that depends on being
            // connected (e.g. getResponseCode, getContentLength, getInputStream), will implicitly
            // perform the connection if necessary. I like having it just for an explicit point
            // of connection when network traffic happens.
            //
            // https://docs.oracle.com/javase/7/docs/api/java/net/URLConnection.html#connect()
            // https://stackoverflow.com/a/30388331/1438339
            httpURLConnection.connect();

            int statusCode = httpURLConnection.getResponseCode();

            // Process response body
            inputStream = httpURLConnection.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }

            String responseString = stringBuilder.toString();

            try {
                Object responseBody = new JSONTokener(responseString).nextValue();
                return new HttpResponse(statusCode, httpURLConnection.getHeaderFields(), responseBody);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return null;
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
