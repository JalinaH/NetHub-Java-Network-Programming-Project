package com.client.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Link Checker Service - Validates URLs using HttpURLConnection
 * Checks HTTP status codes and connection validity
 */
public class LinkCheckerService {
    
    private static final int TIMEOUT = 5000; // 5 seconds
    
    /**
     * Result of link checking operation
     */
    public static class LinkCheckResult {
        private final String url;
        private final boolean valid;
        private final int statusCode;
        private final String message;
        private final long responseTime;
        
        public LinkCheckResult(String url, boolean valid, int statusCode, String message, long responseTime) {
            this.url = url;
            this.valid = valid;
            this.statusCode = statusCode;
            this.message = message;
            this.responseTime = responseTime;
        }
        
        public String getUrl() { return url; }
        public boolean isValid() { return valid; }
        public int getStatusCode() { return statusCode; }
        public String getMessage() { return message; }
        public long getResponseTime() { return responseTime; }
        
        @Override
        public String toString() {
            return String.format("[%s] %s - %s (Status: %d, Time: %dms)",
                valid ? "✓" : "✗", url, message, statusCode, responseTime);
        }
    }
    
    /**
     * Check if a URL is valid and accessible
     * @param urlString URL to check
     * @return LinkCheckResult with details
     */
    public LinkCheckResult checkLink(String urlString) {
        long startTime = System.currentTimeMillis();
        
        try {
            // Create URL object
            URL url = new URL(urlString);
            
            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD"); // HEAD is faster than GET
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setInstanceFollowRedirects(true);
            
            // Set user agent to avoid blocks
            connection.setRequestProperty("User-Agent", "NetHub-LinkChecker/1.0");
            
            // Get response code
            int statusCode = connection.getResponseCode();
            long responseTime = System.currentTimeMillis() - startTime;
            
            // Disconnect
            connection.disconnect();
            
            // Check if valid (2xx or 3xx)
            boolean valid = (statusCode >= 200 && statusCode < 400);
            String message = getStatusDescription(statusCode);
            
            return new LinkCheckResult(urlString, valid, statusCode, message, responseTime);
            
        } catch (MalformedURLException e) {
            long responseTime = System.currentTimeMillis() - startTime;
            return new LinkCheckResult(urlString, false, -1, "Invalid URL format: " + e.getMessage(), responseTime);
            
        } catch (UnknownHostException e) {
            long responseTime = System.currentTimeMillis() - startTime;
            return new LinkCheckResult(urlString, false, -1, "Unknown host: " + e.getMessage(), responseTime);
            
        } catch (IOException e) {
            long responseTime = System.currentTimeMillis() - startTime;
            return new LinkCheckResult(urlString, false, -1, "Connection error: " + e.getMessage(), responseTime);
        }
    }
    
    /**
     * Get human-readable description of HTTP status code
     * @param statusCode HTTP status code
     * @return Description
     */
    private String getStatusDescription(int statusCode) {
        switch (statusCode) {
            case 200: return "OK";
            case 201: return "Created";
            case 204: return "No Content";
            case 301: return "Moved Permanently";
            case 302: return "Found (Redirect)";
            case 304: return "Not Modified";
            case 400: return "Bad Request";
            case 401: return "Unauthorized";
            case 403: return "Forbidden";
            case 404: return "Not Found";
            case 500: return "Internal Server Error";
            case 502: return "Bad Gateway";
            case 503: return "Service Unavailable";
            default: return "HTTP " + statusCode;
        }
    }
}
