package com.disrupture.httpsession;

import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Map;

class HttpConnection {
    static HttpResponse sendRequest(String requestMethod,
                                    SafeUrl url,
                                    Map<String, String> headers) {
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
            // TODO: WHAT ABOUT A 300 RESPONSE

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
}
