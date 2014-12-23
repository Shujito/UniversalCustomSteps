package org.shujito.ucs.models;

import com.google.gson.annotations.SerializedName;

public class UserCustomStep
{
	public static final String TAG = UserCustomStep.class.getSimpleName();
	public static final String ID = "id";
	public static final String CREATOR_ID = "creator_id";
	public static final String STEP_DATA = "step_data";
	@SerializedName(value = ID)
	public String id;
	@SerializedName(value = CREATOR_ID)
	public String creatorId;
	@SerializedName(value = STEP_DATA)
	public String stepData;
}
