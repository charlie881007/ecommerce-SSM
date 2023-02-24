package com.yhlin.util;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Sha256Hasher {
    private final MessageDigest digest;

    public Sha256Hasher() {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String hash(String originalString) {
        byte[] encodedHash = digest.digest(
                originalString.getBytes(StandardCharsets.UTF_8));
        return convertBytesToStr(encodedHash);
    }

    private String convertBytesToStr(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
