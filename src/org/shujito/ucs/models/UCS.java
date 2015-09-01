package org.shujito.ucs.models;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class UCS
{
	public static final String TAG = UCS.class.getSimpleName();
	public static final String UUID = "uuid";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";
	public static final String DELETED_AT = "deleted_at";
	public static final String SONG_ID = "song_id";
	public static final String USER_UUID = "user_uuid";
	public static final String CHART_DATA = "chart_data";
	public static final String DESCRIPTION = "description";
	public static final String DIFFICULTY = "difficulty";
	@SerializedName(UUID)
	private String uuid;
	@SerializedName(CREATED_AT)
	private Date createdAt;
	@SerializedName(UPDATED_AT)
	private Date updatedAt;
	@SerializedName(DELETED_AT)
	private Date deletedAt;
	@SerializedName(SONG_ID)
	private Integer songId;
	@SerializedName(USER_UUID)
	private String userUuid;
	@SerializedName(CHART_DATA)
	private byte[] chartData;
	@SerializedName(DESCRIPTION)
	private String description;
	@SerializedName(DIFFICULTY)
	private String difficulty;
	
	public String getUuid()
	{
		return this.uuid;
	}
	
	public Date getCreatedAt()
	{
		return this.createdAt;
	}
	
	public Date getUpdatedAt()
	{
		return this.updatedAt;
	}
	
	public Date getDeletedAt()
	{
		return this.deletedAt;
	}
	
	public Integer getSongId()
	{
		return this.songId;
	}
	
	public String getUserUuid()
	{
		return this.userUuid;
	}
	
	public byte[] getChartData()
	{
		return this.chartData;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getDifficulty()
	{
		return this.difficulty;
	}
}
