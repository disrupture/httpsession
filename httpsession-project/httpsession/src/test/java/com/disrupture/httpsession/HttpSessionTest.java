package com.disrupture.httpsession;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpSessionTest {
    @Test
    public void emptyConstructor_exists() {
        @SuppressWarnings("unused")
        HttpSession httpSession = new HttpSession();
        assertNull(httpSession.getBaseUrl());
    }

    @Test
    public void baseUrlConstructor_exists() {
        String baseUrl = "https://jsonplaceholder.typicode.com/";
        HttpSession httpSession = new HttpSession(baseUrl);
        assertEquals(baseUrl, httpSession.getBaseUrl());
    }

    @Test
    public void setBaseUrl() {
        String baseUrl = "https://jsonplaceholder.typicode.com/";
        HttpSession httpSession = new HttpSession();
        httpSession.setBaseUrl(baseUrl);
        assertEquals(baseUrl, httpSession.getBaseUrl());
    }
}
