package org.shujito.ucs.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ucs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserCustomSteps
{
	public static final String TAG = UserCustomSteps.class.getSimpleName();
	
	@GET
	public Response index()
	{
		throw new UnsupportedOperationException();
	}
	
	@POST
	public Response post()
	{
		throw new UnsupportedOperationException();
	}
	
	@PUT
	private Response put()
	{
		throw new UnsupportedOperationException();
	}
	
	@GET
	@Path("/{ucs-id}")
	public Response ucs(@PathParam("ucs-id") String ucsID)
	{
		throw new UnsupportedOperationException();
	}
}
