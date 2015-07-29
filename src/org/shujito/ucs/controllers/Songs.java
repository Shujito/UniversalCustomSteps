package org.shujito.ucs.controllers;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.shujito.ucs.models.Song;

@Path("/songs")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class Songs
{
	@GET
	public synchronized Response index() throws Exception
	{
		return Response.ok(Song.getAll()).build();
	}
}