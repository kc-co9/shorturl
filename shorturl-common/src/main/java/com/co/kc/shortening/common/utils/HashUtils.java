package com.co.kc.shortening.common.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * @author kc
 */
public class HashUtils {
    private HashUtils() {
    }

    public static String murmurHash32(String s) {
        return String.valueOf(Hashing.murmur3_32_fixed().hashString(s, StandardCharsets.UTF_8).asInt());
    }
}

