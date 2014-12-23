package org.shujito.ucs.models;

import com.google.gson.annotations.SerializedName;

public class Song
{
	public static final String TAG = Song.class.getSimpleName();
	public static final String ID = "id";
	public static final String UCS = "ucs";
	public static final String BPM = "bpm";
	public static final String DELAY = "delay";
	public static final String ARTIST = "artist";
	public static final String NAME = "name";
	@SerializedName(value = ID)
	public int id;
	@SerializedName(value = UCS)
	public String ucs;
	@SerializedName(value = BPM)
	public double bpm;
	@SerializedName(value = DELAY)
	public double delay;
	@SerializedName(value = ARTIST)
	public String artist;
	@SerializedName(value = NAME)
	public String name;
}
