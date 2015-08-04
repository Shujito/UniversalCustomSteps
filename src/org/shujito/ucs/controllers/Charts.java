package org.shujito.ucs.controllers;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.shujito.ucs.ApiException;

@Path("/charts")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Charts
{
	public static final String TAG = Charts.class.getSimpleName();
	
	@GET
	public synchronized Response index() throws Exception
	{
		throw new ApiException("not yet.", Status.FORBIDDEN.getStatusCode());
	}
}
