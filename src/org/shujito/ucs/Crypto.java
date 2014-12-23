package org.shujito.ucs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto
{
	public static final String TAG = Crypto.class.getSimpleName();
	
	public static byte[] sha256(byte[] bytes) throws NoSuchAlgorithmException
	{
		return MessageDigest.getInstance("SHA-256").digest(bytes);
	}
}
