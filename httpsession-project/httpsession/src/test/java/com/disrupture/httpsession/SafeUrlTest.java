package com.disrupture.httpsession;

import org.junit.Test;

import static org.junit.Assert.*;

public class SafeUrlTest {
    @Test
    public void constructor_nullBaseUrl() {
        SafeUrl safeUrl = new SafeUrl(null, "/posts");
        assertNull(safeUrl.getUrl());
    }

    @Test
    public void constructor_nullPath() {
        SafeUrl safeUrl = new SafeUrl("https://jsonplaceholder.typicode.com", null);
        assertNull(safeUrl.getUrl());
    }

    @Test
    public void constructor_emptyBaseUrl() {
        SafeUrl safeUrl = new SafeUrl("", "/posts");
        assertNull(safeUrl.getUrl());
    }

    @Test
    public void constructor_emptyPath() {
        SafeUrl safeUrl = new SafeUrl("https://jsonplaceholder.typicode.com", "");
        assertNull(safeUrl.getUrl());
    }

    @Test
    public void constructor_malformedBase_badProtocol() {
        SafeUrl safeUrl = new SafeUrl("httpz://jsonplaceholder.typicode.com", "/posts");
        assertNull(safeUrl.getUrl());
    }

    @Test
    public void constructor_malformedBase_noProtocol() {
        SafeUrl safeUrl = new SafeUrl("jsonplaceholder.typicode.com", "/posts");
        assertNull(safeUrl.getUrl());
    }

    @Test
    public void constructor_doubleSlashConnectingBaseWithPath() {
        SafeUrl safeUrl = new SafeUrl("https://jsonplaceholder.typicode.com/", "/posts");
        assertEquals("https://jsonplaceholder.typicode.com/posts", safeUrl.getUrl().toString());
    }

    @Test
    public void constructor_slashOnNetherBaseNorPath() {
        SafeUrl safeUrl = new SafeUrl("https://jsonplaceholder.typicode.com", "posts");
        assertEquals("https://jsonplaceholder.typicode.com/posts", safeUrl.getUrl().toString());
    }

    @Test
    public void constructor_slashOnlyOnBase() {
        SafeUrl safeUrl = new SafeUrl("https://jsonplaceholder.typicode.com/", "posts");
        assertEquals("https://jsonplaceholder.typicode.com/posts", safeUrl.getUrl().toString());
    }

    @Test
    public void constructor_slashOnlyOnPath() {
        SafeUrl safeUrl = new SafeUrl("https://jsonplaceholder.typicode.com", "/posts");
        assertEquals("https://jsonplaceholder.typicode.com/posts", safeUrl.getUrl().toString());
    }
}
