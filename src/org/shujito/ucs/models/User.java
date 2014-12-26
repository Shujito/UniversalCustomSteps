package org.shujito.ucs.models;

import org.shujito.ucs.Constants;

import com.google.gson.annotations.SerializedName;

public class User
{
	public static class Validation
	{
		public final boolean username;
		public final boolean password;
		public final boolean email;
		public final boolean token;
		
		public Validation()
		{
			this.username = false;
			this.password = false;
			this.email = false;
			this.token = false;
		}
		
		public Validation(boolean username, boolean password, boolean email, boolean token)
		{
			this.username = username;
			this.password = password;
			this.email = email;
			this.token = token;
		}
	}
	
	public static final String TAG = User.class.getSimpleName();
	public static final String ID = "id";
	public static final String DISPLAY_NAME = "display_name";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String TOKEN = "token";
	public static final String CREATED = "created";
	@SerializedName(value = ID)
	public String id;
	@SerializedName(value = DISPLAY_NAME)
	public String displayName;
	@SerializedName(value = USERNAME)
	public String username;
	@SerializedName(value = PASSWORD)
	public String password;
	@SerializedName(value = EMAIL)
	public String email;
	@SerializedName(value = TOKEN)
	public String token;
	@SerializedName(value = CREATED)
	public Long created;
	
	public String validate(Validation require)
	{
		// check required fields
		if (require.username && this.username == null)
		{
			return Constants.Strings.NO_USERNAME_SPECIFIED;
		}
		if (require.password && this.password == null)
		{
			return Constants.Strings.NO_PASSWORD_SPECIFIED;
		}
		if (require.email && this.email == null)
		{
			return Constants.Strings.NO_EMAIL_SPECIFIED;
		}
		if (require.token && this.token == null)
		{
			return Constants.Strings.NO_TOKEN_SPECIFIED;
		}
		// lengths
		if (require.password && this.password.length() < 10)
		{
			return Constants.Strings.PASSWORD_IS_TOO_SHORT;
		}
		if (require.token && this.token.length() != 64)
		{
			return Constants.Strings.TOKEN_MUST_BE_64_CHARACTERS_LONG;
		}
		return null;
	}
}
