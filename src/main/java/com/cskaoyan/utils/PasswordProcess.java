package com.cskaoyan.utils;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordProcess {

    public static String passwordProcess(String srcCode) {
        srcCode = srcCode + "Group 9";
        MessageDigest messageDigest = null;
        String desCode = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] srcByte = messageDigest.digest(srcCode.getBytes("UTF-8"));
            desCode = Hex.encodeHexString(srcByte);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return desCode;
    }
}
