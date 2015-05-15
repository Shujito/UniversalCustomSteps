package org.shujito.ucs.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.shujito.ucs.ApiException;
import org.shujito.ucs.GsonWrapper;
import org.shujito.ucs.models.User;

import com.google.gson.Gson;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Users
{
	public static final String TAG = Users.class.getSimpleName();
	private final Gson gson = GsonWrapper.getInstance().getGson();
	
	@GET
	public Response index()
	{
		throw new ApiException("Not yet", Status.NOT_FOUND.getStatusCode());
	}
	
	@POST
	@Path("/register")
	public Response register(User user) throws Exception
	{
		System.out.println(this.gson.toJson(user));
		throw new ApiException("Not yet", Status.NOT_FOUND.getStatusCode());
		//return Response.ok(user).build();
	}
}
