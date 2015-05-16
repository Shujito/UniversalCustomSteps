package org.shujito.ucs.models;

import java.security.SecureRandom;
import java.sql.PreparedStatement;

import org.shujito.ucs.Crypto;
import org.shujito.ucs.db.Database;

public class UserPassword
{
	public static final String TAG = UserPassword.class.getSimpleName();
	public static final String USER_UUID = "user_uuid";
	public static final String PASSWORD = "password";
	public static final String SALT = "salt";
	public String user;
	public String password;
	public String salt;
	
	public UserPassword(String password)
	{
		this.password = password;
		this.hashPassword();
	}
	
	public void hashPassword()
	{
		byte[] saltBytes = new byte[32];
		new SecureRandom().nextBytes(saltBytes);
		this.hashPassword(saltBytes);
	}
	
	public void hashPassword(byte[] saltBytes)
	{
		byte[] passwordBytes = this.password.getBytes();
		byte[] saltedPasswordBytes = new byte[passwordBytes.length + saltBytes.length];
		for (int idx = 0; idx < saltBytes.length; idx++)
			saltedPasswordBytes[idx] = saltBytes[idx];
		for (int idx = 0; idx < passwordBytes.length; idx++)
			saltedPasswordBytes[idx + saltBytes.length] = passwordBytes[idx];
		// hash it
		byte[] sha256passwordBytes = Crypto.sha256(saltedPasswordBytes);
		// stretchy
		byte[] bytesContainer = new byte[sha256passwordBytes.length + saltBytes.length];
		for (int idx = 0; idx < 0x7ffff; idx++)
		{
			for (int jdx = 0; jdx < saltBytes.length; jdx++)
				bytesContainer[jdx] = saltBytes[jdx];
			for (int jdx = 0; jdx < sha256passwordBytes.length; jdx++)
				bytesContainer[jdx + saltBytes.length] = sha256passwordBytes[jdx];
			sha256passwordBytes = Crypto.sha256(bytesContainer);
		}
		this.password = Crypto.base64encode(sha256passwordBytes);
		this.salt = Crypto.base64encode(saltBytes);
	}
	
	public void save() throws Exception
	{
		try (PreparedStatement psm = Database
			//.prepareStatement("insert into user_passwords(user_uuid,password,salt) values (?,?,?)"))
			.prepareStatement("insert into user_passwords(user_uuid,password,salt) select users.uuid as user_uuid,? as password,? as salt from users where users.username = ?"))
		{
			psm.setString(1, this.password);
			psm.setString(2, this.salt);
			psm.setString(3, this.user);
			psm.executeUpdate();
		}
	}
}
