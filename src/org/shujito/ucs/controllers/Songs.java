package org.shujito.ucs.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.shujito.ucs.ApiException;

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
	public Response index() throws Exception
	{
		throw new ApiException(null, Status.NOT_IMPLEMENTED.getStatusCode());
	}
	
	/**
	 * Get a single song object
	 * @param id of the song
	 * @return a single song object
	 */
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") String id)
	{
		throw new ApiException(null, Status.NOT_IMPLEMENTED.getStatusCode());
	}
}
