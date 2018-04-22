package com.disrupture.sampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.disrupture.httpsession.HttpResponse;
import com.disrupture.httpsession.HttpResponseHandler;
import com.disrupture.httpsession.HttpSession;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpSession httpSession = new HttpSession("https://jsonplaceholder.typicode.com/");
        httpSession.get("/posts", new HttpResponseHandler() {
            @Override
            public void onSuccess(HttpResponse response) {
                Log.d("tagzzz", response.getBody() + "");
            }
        });
    }
}
