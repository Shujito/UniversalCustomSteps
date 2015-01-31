package org.shujito.ucs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Crypto
{
	public static final String TAG = Crypto.class.getSimpleName();
	
	public static byte[] digest(byte[] bytes, String algorithm)
	{
		try
		{
			return MessageDigest.getInstance(algorithm).digest(bytes);
		}
		catch (NoSuchAlgorithmException ex)
		{
			return null;
		}
	}
	
	public static byte[] sha256(byte[] bytes)
	{
		return digest(bytes, "SHA-256");
	}
	
	public static byte[] sha512(byte[] bytes)
	{
		return digest(bytes, "SHA-512");
	}
	
	public static byte[] base64decode(String base64String)
	{
		return Base64.getDecoder().decode(base64String);
	}
	
	public static String base64encode(byte[] rawBytes)
	{
		return Base64.getEncoder().encodeToString(rawBytes);
	}
	
	public static byte[] compressString(String s)
	{
		throw new UnsupportedOperationException();
	}
	
	public static String decompressString(byte[] b)
	{
		throw new UnsupportedOperationException();
	}
}
