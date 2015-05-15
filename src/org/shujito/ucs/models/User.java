package org.shujito.ucs.models;

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
	@SerializedName(UUID)
	public String uuid;
	@SerializedName(CREATED_AT)
	public String createdAt;
	@SerializedName(UPDATED_AT)
	public String updatedAt;
	@SerializedName(DELETED_AT)
	public String deletedAt;
	@SerializedName(USERNAME)
	public String username;
	@SerializedName(DISPLAY_NAME)
	public String displayName;
	@SerializedName(PASSWORD)
	public String password;
	@SerializedName(EMAIL)
	public String email;
}
