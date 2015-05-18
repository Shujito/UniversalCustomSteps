package org.shujito.ucs.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.validator.routines.EmailValidator;
import org.shujito.ucs.ApiException;
import org.shujito.ucs.Constants;
import org.shujito.ucs.db.Database;

import com.google.gson.annotations.SerializedName;

public class User
{
	public static class Validation
	{
		public final boolean username;
		public final boolean password;
		public final boolean email;
		
		public Validation()
		{
			this.username = false;
			this.password = false;
			this.email = false;
		}
		
		public Validation(boolean username, boolean password, boolean email)
		{
			this.username = username;
			this.password = password;
			this.email = email;
		}
	}
	
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
	
	public void validate(Validation validate)
	{
		if (validate.username && this.username == null)
			throw new ApiException(Constants.Strings.NO_USERNAME_SPECIFIED, Status.NOT_ACCEPTABLE.getStatusCode());
		if (validate.password && this.password == null)
			throw new ApiException(Constants.Strings.NO_PASSWORD_SPECIFIED, Status.NOT_ACCEPTABLE.getStatusCode());
		if (validate.email && this.email == null)
			throw new ApiException(Constants.Strings.NO_EMAIL_SPECIFIED, Status.NOT_ACCEPTABLE.getStatusCode());
		// lengths
		if (validate.password && this.password.length() < 10)
			throw new ApiException(Constants.Strings.PASSWORD_IS_TOO_SHORT, Status.NOT_ACCEPTABLE.getStatusCode());
		// formats
		if (validate.username && !Pattern.matches("[a-zA-Z]{2,24}", this.username))
			throw new ApiException(Constants.Strings.USERNAME_CAN_ONLY_CONTAIN_BETWEEN_2_AND_24_LETTERS, Status.NOT_ACCEPTABLE.getStatusCode());
		if (validate.email && !EmailValidator.getInstance().isValid(this.email))
			throw new ApiException(Constants.Strings.INVALID_EMAIL_ADDRESS, Status.NOT_ACCEPTABLE.getStatusCode());
	}
	
	public void load() throws Exception
	{
		try (PreparedStatement psm = Database.prepareStatement("select uuid,created_at,updated_at,deleted_at,username,display_name from users where username = lower(?) and deleted_at is null"))
		{
			psm.setString(1, this.username);
			try (ResultSet rs = psm.executeQuery())
			{
				if (!rs.next())
					throw new ApiException(Constants.Strings.USER_DOES_NOT_EXIST, Status.NOT_FOUND.getStatusCode());
				User user = User.fromResultSet(rs);
				this.uuid = user.uuid;
				this.createdAt = user.createdAt;
				this.updatedAt = user.updatedAt;
				this.deletedAt = user.deletedAt;
				this.username = user.username;
				this.displayName = user.displayName;
			}
		}
	}
	
	public void save() throws Exception
	{
		try (PreparedStatement psm = Database.prepareStatement("insert into users(username,display_name,email) values(lower(?),?,?)"))
		{
			psm.setString(1, this.username);
			psm.setString(2, this.username);
			psm.setString(3, this.email);
			psm.executeUpdate();
		}
		catch (SQLException ex)
		{
			throw new ApiException(ex.getMessage(), Status.CONFLICT.getStatusCode());
		}
		try (PreparedStatement psm = Database.prepareStatement("select uuid from users where username=lower(?)"))
		{
			psm.setString(1, this.username);
			try (ResultSet rs = psm.executeQuery())
			{
				if (rs.next())
					this.uuid = rs.getString("uuid");
			}
		}
	}
}
