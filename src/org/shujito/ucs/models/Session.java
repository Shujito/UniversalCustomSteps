package org.shujito.ucs.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.core.Response.Status;

import org.shujito.ucs.ApiException;
import org.shujito.ucs.Constants;
import org.shujito.ucs.db.Database;

import com.google.gson.annotations.SerializedName;

public class Session
{
	public static final String TAG = Session.class.getSimpleName();
	public static final String ACCESS_TOKEN = "access_token";
	public static final String EXPIRES_AT = "expires_at";
	public static final String USER_AGENT = "user_agent";
	public static final String USER_UUID = "user_uuid";
	@SerializedName(USER_UUID)
	public final String userUuid;
	@SerializedName(ACCESS_TOKEN)
	public byte[] accessToken;
	@SerializedName(EXPIRES_AT)
	public Long expiresAt;
	@SerializedName(USER_AGENT)
	public String userAgent;
	
	public Session(String userUuid, String userAgent)
	{
		this.userUuid = userUuid;
		this.userAgent = userAgent;
	}
	
	public void save() throws Exception
	{
		try (PreparedStatement psm = Database
			.prepareStatement("insert into sessions(user_uuid,user_agent) values(?,?)"))
		{
			psm.setString(1, this.userUuid);
			psm.setString(2, this.userAgent);
			if (psm.executeUpdate() == 0)
				throw new ApiException(Constants.Strings.USER_DOES_NOT_EXIST, Status.NOT_FOUND.getStatusCode());
		}
		try (PreparedStatement psm = Database
			.prepareStatement("select expires_at, access_token from sessions where user_uuid=? order by expires_at desc limit 1"))
		{
			psm.setString(1, this.userUuid);
			try (ResultSet rs = psm.executeQuery())
			{
				if (rs.next())
				{
					this.accessToken = rs.getBytes("access_token");
					this.expiresAt = rs.getLong("expires_at");
				}
			}
		}
	}
}
