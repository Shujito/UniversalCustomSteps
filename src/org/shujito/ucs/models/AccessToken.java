package org.shujito.ucs.models;

import com.google.gson.annotations.SerializedName;

public class AccessToken
{
	public static final String TAG = AccessToken.class.getSimpleName();
	public static final String ACCESS_TOKEN = "access_token";
	public static final String REFRESH = "refresh";
	public static final String EXPIRES = "expires";
	@SerializedName(value = ACCESS_TOKEN)
	public byte[] accessToken;
	@SerializedName(value = REFRESH)
	public String refresh;
	@SerializedName(value = EXPIRES)
	public Long expires;
}
