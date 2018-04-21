package com.disrupture.httpsession;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class for making sure double slashes '//' connecting base URLs with path endpoints
 * still allows the URL to be created.
 *
 * e.g. https://jsonplaceholder.typicode.com//posts should be transformed to
 * https://jsonplaceholder.typicode.com/posts before creating the URL.
 *
 * This class just serves as a convenience for the library to still work in the case of
 * a minor error in forming the URL.
 */
class SafeUrl {
    private URL url;

    SafeUrl(String baseUrl, String path) {
        if (baseUrl == null ||
            baseUrl.equals("") ||
            path == null ||
            path.equals("")) {
            return;
        }

        // If base url is malformed in any way, just exit
        try {
            new URL(baseUrl);
        } catch (MalformedURLException e) {
            return;
        }

        // Remove '/' from the end of baseUrl if it is there
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }

        // Add '/' to the beginning of path if it isn't already there
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        try {
            this.url = new URL(baseUrl + path);
        } catch (MalformedURLException ignored) {
        }
    }

    URL getUrl() {
        return url;
    }
}
