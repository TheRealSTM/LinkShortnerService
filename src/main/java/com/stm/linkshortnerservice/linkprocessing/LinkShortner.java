package com.stm.linkshortnerservice.linkprocessing;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LinkShortner {
    private static final MessageDigest DIGESTER;
    private static final int SHORT_URL_LENGTH = 7;
    static {
        try {
            DIGESTER = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
    private final Map<String, String> shortenedUrlFullUrlMap = new ConcurrentHashMap<>();

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String shortenLink(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        String hashedURL = hashUrl(url);
        shortenedUrlFullUrlMap.put(hashedURL, url);

        return hashedURL;
    }

    public String getFullUrl(String hashedUrl) {
        if (hashedUrl == null || hashedUrl.isEmpty()) {
            throw new IllegalArgumentException("Hashed URL cannot be null or empty");
        }
        String fullUrl = shortenedUrlFullUrlMap.get(hashedUrl);
        if (fullUrl == null) {
            throw new IllegalArgumentException("No mapping found for the given hashed URL");
        }
        return fullUrl;
    }

    private String hashUrl(String url) {
        byte[] hashBytes = DIGESTER.digest(url.getBytes());

        BigInteger hashValue = new BigInteger(1, hashBytes);

        StringBuilder shortenedUrl = new StringBuilder();
        while (hashValue.compareTo(BigInteger.ZERO) > 0 && shortenedUrl.length() < SHORT_URL_LENGTH) {
            shortenedUrl.append(BASE62.charAt(hashValue.mod(BigInteger.valueOf(62)).intValue()));
            hashValue = hashValue.divide(BigInteger.valueOf(62));
        }
        while (shortenedUrl.length() < SHORT_URL_LENGTH) {
            shortenedUrl.append('0');
        }
        return shortenedUrl.toString();
    }
}
