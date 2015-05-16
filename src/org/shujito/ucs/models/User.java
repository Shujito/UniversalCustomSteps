package org.shujito.ucs.models;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.ws.rs.core.Response.Status;

import org.shujito.ucs.ApiException;
import org.shujito.ucs.Constants;
import org.shujito.ucs.Crypto;
import org.shujito.ucs.db.Database;

import com.google.gson.annotations.SerializedName;

public class User
{
	public static final String TAG = User.class.getSimpleName();
	public static final String TABLE = "users";
	public static final String UUID = "uuid";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";
	public static final String DELETED_AT = "deleted_at";
	public static final String USERNAME = "username";
	public static final String DISPLAY_NAME = "display_name";
	public static final String PASSWORD = "password";
	public static final String PASSWORD_SALT = "password_salt";
	public static final String EMAIL = "email";
	
	public static User fromResultSet(ResultSet rs) throws Exception
	{
		ResultSetMetaData rsmd = rs.getMetaData();
		User user = new User();
		int count = rsmd.getColumnCount();
		for (int idx = 1; idx <= count; idx++)
		{
			switch (rsmd.getColumnLabel(idx))
			{
				case UUID:
					user.uuid = rs.getString(idx);
					break;
				case CREATED_AT:
					user.createdAt = rs.getLong(idx);
					break;
				case UPDATED_AT:
					user.updatedAt = rs.getLong(idx);
					break;
				case DELETED_AT:
					user.deletedAt = rs.getLong(idx);
					break;
				case USERNAME:
					user.username = rs.getString(idx);
					break;
				case DISPLAY_NAME:
					user.displayName = rs.getString(idx);
					break;
				case PASSWORD:
					user.password = rs.getString(idx);
					break;
				case EMAIL:
					user.email = rs.getString(idx);
					break;
			}
		}
		return user;
	}
	
	@SerializedName(UUID)
	public String uuid;
	@SerializedName(CREATED_AT)
	public Long createdAt;
	@SerializedName(UPDATED_AT)
	public Long updatedAt;
	@SerializedName(DELETED_AT)
	public Long deletedAt;
	@SerializedName(USERNAME)
	public String username;
	@SerializedName(DISPLAY_NAME)
	public String displayName;
	@SerializedName(EMAIL)
	public String email;
	@SerializedName(PASSWORD)
	public String password;
	
	public void validate()
	{
		if (this.username == null)
			throw new ApiException(Constants.Strings.NO_USERNAME_SPECIFIED, Status.NOT_ACCEPTABLE.getStatusCode());
		if (this.password == null)
			throw new ApiException(Constants.Strings.NO_PASSWORD_SPECIFIED, Status.NOT_ACCEPTABLE.getStatusCode());
		if (this.email == null)
			throw new ApiException(Constants.Strings.NO_EMAIL_SPECIFIED, Status.NOT_ACCEPTABLE.getStatusCode());
		if (this.username.length() < 2 || this.username.length() > 24)
			throw new ApiException(Constants.Strings.USERNAME_CAN_ONLY_CONTAIN_BETWEEN_2_AND_24_LETTERS, Status.NOT_ACCEPTABLE.getStatusCode());
		if (this.password.length() < 10)
			throw new ApiException(Constants.Strings.PASSWORD_IS_TOO_SHORT, Status.NOT_ACCEPTABLE.getStatusCode());
	}
	
	public void hashPassword()
	{
		byte[] saltBytes = new byte[16];
		new SecureRandom().nextBytes(saltBytes);
		this.hashPassword(saltBytes);
	}
	
	public void hashPassword(byte[] saltBytes)
	{
		byte[] passwordBytes = this.password.getBytes();
		byte[] saltedPasswordBytes = new byte[passwordBytes.length + saltBytes.length];
		for (int idx = 0; idx < passwordBytes.length; idx++)
			saltedPasswordBytes[idx] = passwordBytes[idx];
		for (int idx = 0; idx < saltBytes.length; idx++)
			saltedPasswordBytes[idx + saltBytes.length] = saltBytes[idx];
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
	}
	
	public void save() throws Exception
	{
		try (PreparedStatement psm = Database.prepareStatement("insert into users(username,display_name,email) values(?,?,?)"))
		{
			psm.setString(1, this.username == null ? this.username : this.username.toLowerCase());
			//psm.setString(2, user.displayName == null ? user.username : user.displayName);
			psm.setString(2, this.username);
			//psm.setString(3, this.password);
			psm.setString(3, this.email);
			psm.executeUpdate();
		}
		catch (SQLException ex)
		{
			throw new ApiException(ex.getMessage(), Status.CONFLICT.getStatusCode());
		}
	}
}
