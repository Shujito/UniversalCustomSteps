package org.shujito.ucs.models;

import java.util.Date;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;

public class UserCustomStep
{
	public static final String TAG = UserCustomStep.class.getSimpleName();
	public static final String ID = "id";
	public static final String SONG_ID = "song_id";
	public static final String CREATOR_ID = "creator_id";
	public static final String STEP_DATA = "step_data";
	public static final String DESCRIPTION = "description";
	public static final String DIFFICULTY = "difficulty";
	public static final String CREATED_AT = "created_at";
	public static final String MODIFIED_AT = "modified_at";
	
	public UserCustomStep()
	{
		this.id = UUID.randomUUID().toString().replace("-", "");
		this.modifiedAt = this.createdAt = new Date();
	}
	
	@SerializedName(value = ID)
	public String id;
	@SerializedName(value = SONG_ID)
	public String songId;
	@SerializedName(value = CREATOR_ID)
	public String creatorId;
	@SerializedName(value = STEP_DATA)
	public String stepData;
	@SerializedName(value = DESCRIPTION)
	public String description;
	@SerializedName(value = DIFFICULTY)
	public Integer difficulty;
	@SerializedName(value = CREATED_AT)
	public Date createdAt;
	@SerializedName(value = MODIFIED_AT)
	public Date modifiedAt;
}
