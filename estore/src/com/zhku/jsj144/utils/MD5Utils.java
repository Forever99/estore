package com.zhku.jsj144.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * 使用md5的算法进行加密
	 * 
	 * @param plainText
	 *            加密原文
	 * @return 加密密文
	 */
	public static String md5(String plainText) {//传入要加密的数据，返回一个加密后的数据
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}
		return new BigInteger(1, secretBytes).toString(16);
	}
}
