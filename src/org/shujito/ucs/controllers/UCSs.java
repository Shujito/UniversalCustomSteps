package org.shujito.ucs.controllers;

import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.shujito.ucs.ApiException;
import org.shujito.ucs.models.UCS;
import org.shujito.ucs.models.User;

@Path("/ucs")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UCSs
{
	public static final String TAG = UCSs.class.getSimpleName();
	
	@GET
	public synchronized Response index() throws Exception
	{
		throw new ApiException("not yet.", Status.NOT_IMPLEMENTED.getStatusCode());
	}
	
	@POST
	public synchronized Response post(@BeanParam User user, UCS ucs) throws Exception
	{
		user.continueOrThrow();
		throw new ApiException("can't yet.", Status.NOT_IMPLEMENTED.getStatusCode());
	}
}
