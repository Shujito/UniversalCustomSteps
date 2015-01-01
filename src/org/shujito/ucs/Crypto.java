package org.shujito.ucs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

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
		try
		{
			return Base64.decode(base64String);
		}
		catch (Base64DecodingException e)
		{
			return null;
		}
	}
	
	public static String base64encode(byte[] base64bytes)
	{
		return Base64.encode(base64bytes);
	}
}
