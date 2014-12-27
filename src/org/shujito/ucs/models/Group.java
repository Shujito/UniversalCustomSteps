package org.shujito.ucs.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Group
{
	public static final String TAG = Group.class.getSimpleName();
	public static final String NAME = "name";
	public static final String SONGS = "songs";
	@SerializedName(value = NAME)
	public final String name;
	@SerializedName(value = SONGS)
	public final List<Song> songs;
	
	public Group(String name, List<Song> songs)
	{
		this.name = name;
		this.songs = songs;
	}
}
