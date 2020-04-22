package com.hs.utils;

import java.util.UUID;

public class UUIDUtils {
    /**
     * �������id
     */
    public static String getId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * ���������
     */
    public static String getToken() {
        return getId();
    }

    public static void main(String[] args) {
        System.out.println(getId());
    }
}
