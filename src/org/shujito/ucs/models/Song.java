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
	public final int id;
	@SerializedName(value = UCS)
	public final String ucs;
	@SerializedName(value = BPM)
	public final double bpm;
	@SerializedName(value = DELAY)
	public final double delay;
	@SerializedName(value = ARTIST)
	public final String artist;
	@SerializedName(value = NAME)
	public final String name;
	
	public Song(int id, String ucs, double bpm, double delay, String artist, String name)
	{
		this.id = id;
		this.ucs = ucs;
		this.bpm = bpm;
		this.delay = delay;
		this.artist = artist;
		this.name = name;
	}
}
