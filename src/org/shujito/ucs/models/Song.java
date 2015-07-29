package org.shujito.ucs.models;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.shujito.ucs.db.Database;

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
	
	public static final List<Song> getAll() throws Exception
	{
		try (Statement smt = Database.createStatement())
		{
			try (ResultSet rs = smt.executeQuery("select id,ucs,bpm,delay,artist,name from songs order by ucs"))
			{
				List<Song> songs = new ArrayList<Song>();
				while (rs.next())
				{
					Song song = Song.fromResultSet(rs);
					songs.add(song);
				}
				return songs;
			}
		}
	}
	
	private static Song fromResultSet(ResultSet rs) throws Exception
	{
		ResultSetMetaData rsmd = rs.getMetaData();
		Song song = new Song();
		int count = rsmd.getColumnCount();
		for (int idx = 1; idx <= count; idx++)
		{
			switch (rsmd.getColumnLabel(idx))
			{
				case ID:
					song.id = rs.getInt(idx);
					break;
				case UCS:
					song.ucs = rs.getString(idx);
					break;
				case BPM:
					song.bpm = rs.getDouble(idx);
					break;
				case DELAY:
					song.delay = rs.getDouble(idx);
					break;
				case ARTIST:
					song.artist = rs.getString(idx);
					break;
				case NAME:
					song.name = rs.getString(idx);
					break;
			}
		}
		return song;
	}
	
	@SerializedName(value = ID)
	private int id;
	@SerializedName(value = UCS)
	private String ucs;
	@SerializedName(value = BPM)
	private double bpm;
	@SerializedName(value = DELAY)
	private double delay;
	@SerializedName(value = ARTIST)
	private String artist;
	@SerializedName(value = NAME)
	private String name;
	
	private Song()
	{
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getUcs()
	{
		return this.ucs;
	}
	
	public double getBpm()
	{
		return this.bpm;
	}
	
	public double getDelay()
	{
		return this.delay;
	}
	
	public String getArtist()
	{
		return this.artist;
	}
	
	public String getName()
	{
		return this.name;
	}
}