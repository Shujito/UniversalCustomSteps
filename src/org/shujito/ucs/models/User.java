package org.shujito.ucs.models;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

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
	@SerializedName(PASSWORD)
	public String password;
	@SerializedName(EMAIL)
	public String email;
}
