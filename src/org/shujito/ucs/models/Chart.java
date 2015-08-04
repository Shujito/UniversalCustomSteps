package org.shujito.ucs.models;

import com.google.gson.annotations.SerializedName;

public class Chart
{
	public static final String TAG = Chart.class.getSimpleName();
	public static final String UUID = "uuid";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";
	public static final String DELETED_AT = "deleted_at";
	public static final String SONG_ID = "song_id";
	public static final String USER_UUID = "user_uuid";
	public static final String STEP_DATA = "step_data";
	public static final String DESCRIPTION = "description";
	public static final String DIFFICULTY = "difficulty";
	@SerializedName(UUID)
	private String uuid;
	@SerializedName(CREATED_AT)
	private String createdAt;
	@SerializedName(UPDATED_AT)
	private String updatedAt;
	@SerializedName(DELETED_AT)
	private String deletedAt;
	@SerializedName(SONG_ID)
	private String songId;
	@SerializedName(USER_UUID)
	private String userUuid;
	@SerializedName(STEP_DATA)
	private String stepData;
	@SerializedName(DESCRIPTION)
	private String description;
	@SerializedName(DIFFICULTY)
	private String difficulty;
}
