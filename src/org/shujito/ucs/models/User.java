package org.shujito.ucs.models;

import java.util.Map;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response.Status;

import org.shujito.ucs.ApiException;
import org.shujito.ucs.Constants;
import org.shujito.ucs.Crypto;
import org.shujito.ucs.GsonWrapper;
import org.shujito.ucs.JedisWrapper;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.sun.org.apache.xml.internal.security.utils.Base64;

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
		
		public Validation(boolean username, boolean password, boolean email, boolean token)
		{
			this.username = username;
			this.password = password;
			this.email = email;
		}
	}
	
	public static final String TAG = User.class.getSimpleName();
	public static final String ID = "id";
	public static final String DISPLAY_NAME = "display_name";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String ACCESS_TOKEN = "access_token";
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
	@SerializedName(value = ACCESS_TOKEN)
	public String accessToken;
	@SerializedName(value = CREATED)
	public Long created;
	private Exception exception;
	
	// privates
	public User(@HeaderParam("access-token") String accessToken, @HeaderParam("user-agent") String userAgent)
	{
		if (accessToken == null)
		{
			this.exception = new ApiException(Constants.Strings.ACCESS_DENIED, Status.FORBIDDEN.getStatusCode());
			return;
		}
		if (accessToken.length() != 44)
		{
			this.exception = new ApiException(Constants.Strings.MALFORMED_ACCESS_TOKEN, Status.FORBIDDEN.getStatusCode());
			return;
		}
		byte[] accessTokenBytes = Crypto.base64decode(accessToken);
		if (accessTokenBytes == null)
		{
			this.exception = new ApiException(Constants.Strings.MALFORMED_ACCESS_TOKEN, Status.BAD_REQUEST.getStatusCode());
			return;
		}
		byte[] sha256accessTokenBytes = Crypto.sha256(accessTokenBytes);
		String sha256accessTokenString = Base64.encode(sha256accessTokenBytes);
		Jedis jedis = JedisWrapper.open();
		this.id = jedis.hget("session:" + sha256accessTokenString, User.ID);
		if (this.id == null)
		{
			this.exception = new ApiException(Constants.Strings.ACCESS_DENIED, Status.FORBIDDEN.getStatusCode());
			return;
		}
		Gson gson = GsonWrapper.getInstance().getGson();
		Map<String, String> userMap = jedis.hgetAll("user:" + this.id);
		String userMapJson = gson.toJson(userMap);
		User user = gson.fromJson(userMapJson, User.class);
		//this.id = user.id;
		this.displayName = user.displayName;
		this.username = user.username;
		this.password = user.password;
		this.email = user.email;
		this.created = user.created;
		this.accessToken = sha256accessTokenString;
	}
	
	public void continueOrThrow() throws Exception
	{
		if (this.exception != null)
		{
			throw this.exception;
		}
	}
	
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
		// lengths
		if (require.password && this.password.length() < 10)
		{
			return Constants.Strings.PASSWORD_IS_TOO_SHORT;
		}
		return null;
	}
}
