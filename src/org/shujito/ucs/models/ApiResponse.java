package org.shujito.ucs.models;

import com.google.gson.annotations.SerializedName;

public class ApiResponse
{
    public static final String TAG = ApiResponse.class.getSimpleName();
    public static final String SUCCESS = "success";
    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    @SerializedName(value = SUCCESS)
    public boolean success = true;
    @SerializedName(value = STATUS)
    public int status = 200;
    @SerializedName(value = MESSAGE)
    public String message;
}
