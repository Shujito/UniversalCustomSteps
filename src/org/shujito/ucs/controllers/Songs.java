package org.shujito.ucs.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.shujito.ucs.models.Song;

@Path("songs")
@Produces(MediaType.APPLICATION_JSON)
public class Songs
{
	public static final String TAG = Songs.class.getSimpleName();
	
	/**
	 * Get a list of available songs
	 * @return A list of available songs
	 */
	@GET
	public List<Song> index()
	{
		List<Song> songs = new ArrayList<Song>();
		songs.add(new Song());
		songs.add(new Song());
		songs.add(new Song());
		return songs;
	}
	
	/**
	 * Get a single song object
	 * @param id of the song
	 * @return a single song object
	 */
	@GET
	@Path("{id}")
	public Song get(@PathParam("id") String id)
	{
		Song song = new Song();
		song.name = id;
		//return new Gson().toJson(song);
		return song;
	}
}
