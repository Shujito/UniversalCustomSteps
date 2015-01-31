package org.shujito.ucs.controllers;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.shujito.ucs.ApiException;
import org.shujito.ucs.Constants;
import org.shujito.ucs.models.User;
import org.shujito.ucs.models.UserCustomStep;

@Path("/ucs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserCustomSteps
{
	public static final String TAG = UserCustomSteps.class.getSimpleName();
	
	@GET
	public Response index(@BeanParam User user) throws Exception
	{
		throw new UnsupportedOperationException();
	}
	
	@POST
	public Response post(@BeanParam User user, UserCustomStep ucs) throws Exception
	{
		user.continueOrThrow();
		if (ucs == null)
			throw new ApiException(Constants.Strings.MISSING_CONTENT_BODY, 403);
		throw new UnsupportedOperationException();
	}
	
	@PUT
	public Response put(@BeanParam User user) throws Exception
	{
		user.continueOrThrow();
		throw new UnsupportedOperationException();
	}
	
	@GET
	@Path("/{ucs-id}")
	public Response ucs(@PathParam("ucs-id") String ucsID) throws Exception
	{
		throw new UnsupportedOperationException();
	}
}
