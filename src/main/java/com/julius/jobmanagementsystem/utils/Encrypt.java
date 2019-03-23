package com.julius.jobmanagementsystem.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class Encrypt {
	public static String encodeByMd5(String pass) throws Exception{
		String password = null;
		MessageDigest digest = MessageDigest.getInstance("MD5");
		BASE64Encoder encoder = new BASE64Encoder();
		password = encoder.encode(digest.digest(pass.getBytes("utf-8")));
		return password;
	}
}