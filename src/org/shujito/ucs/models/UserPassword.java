package org.shujito.ucs.models;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.ws.rs.core.Response.Status;

import org.shujito.ucs.ApiException;
import org.shujito.ucs.Constants;
import org.shujito.ucs.Crypto;
import org.shujito.ucs.db.Database;

public class UserPassword
{
	public static final String TAG = UserPassword.class.getSimpleName();
	public static final String USER_UUID = "user_uuid";
	public static final String PASSWORD = "password";
	public static final String SALT = "salt";
	
	public static UserPassword fromResultSet(ResultSet rs) throws Exception
	{
		ResultSetMetaData rsmd = rs.getMetaData();
		UserPassword up = new UserPassword();
		int count = rsmd.getColumnCount();
		for (int idx = 1; idx <= count; idx++)
		{
			switch (rsmd.getColumnLabel(idx))
			{
				case USER_UUID:
					up.userUuid = rs.getString(idx);
					break;
				case PASSWORD:
					up.password = rs.getBytes(idx);
					break;
				case SALT:
					up.salt = rs.getBytes(idx);
					break;
			}
		}
		return up;
	}
	
	public static UserPassword fromUsername(String username) throws Exception
	{
		try (PreparedStatement psm = Database.prepareStatement("select "
			+ "users.uuid as user_uuid,"
			+ "user_passwords.password as password,"
			+ "user_passwords.salt as salt"
			+ " from users"
			+ " inner join user_passwords"
			+ " on users.uuid=user_passwords.user_uuid"
			+ " where users.username=lower(?)"))
		{
			psm.setString(1, username);
			try (ResultSet rs = psm.executeQuery())
			{
				if (!rs.next())
					throw new ApiException(Constants.Strings.USER_DOES_NOT_EXIST, Status.NOT_FOUND.getStatusCode());
				return UserPassword.fromResultSet(rs);
			}
		}
	}
	
	public String userUuid;
	public byte[] password;
	public byte[] salt;
	
	private UserPassword()
	{
	}
	
	public UserPassword(String password)
	{
		this.password = password.getBytes();
		this.hashPassword();
	}
	
	public UserPassword(byte[] password, byte[] salt)
	{
		this.password = password;
		this.salt = salt;
		this.hashPassword(salt);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof UserPassword))
			return false;
		UserPassword cast = UserPassword.class.cast(obj);
		int diff = this.password.length ^ cast.password.length;
		for (int idx = 0; idx < this.password.length && idx < cast.password.length; idx++)
			diff |= this.password[idx] ^ cast.password[idx];
		return diff == 0;
	}
	
	public void hashPassword()
	{
		byte[] saltBytes = new byte[32];
		new SecureRandom().nextBytes(saltBytes);
		this.hashPassword(saltBytes);
	}
	
	public void hashPassword(byte[] saltBytes)
	{
		byte[] passwordBytes = this.password;
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
		this.password = sha256passwordBytes;
		this.salt = saltBytes;
	}
	
	public void save(String username) throws Exception
	{
		try (PreparedStatement psm = Database
			.prepareStatement("insert into user_passwords(user_uuid,password,salt) select users.uuid as user_uuid,? as password,? as salt from users where users.username = lower(?)"))
		{
			psm.setBytes(1, this.password);
			psm.setBytes(2, this.salt);
			psm.setString(3, username);
			psm.executeUpdate();
		}
	}
}
