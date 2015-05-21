package org.shujito.ucs.controllers;

import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.shujito.ucs.ApiException;
import org.shujito.ucs.Constants;
import org.shujito.ucs.models.Session;
import org.shujito.ucs.models.User;
import org.shujito.ucs.models.User.Validation;
import org.shujito.ucs.models.UserPassword;

@Path("/users")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Users
{
	public static final String TAG = Users.class.getSimpleName();
	
	@GET
	public synchronized Response index() throws Exception
	{
		return Response.ok(User.getAll()).build();
	}
	
	@GET
	@Path("{uuid}")
	public synchronized Response index(@PathParam("uuid") String uuid) throws Exception
	{
		return Response.ok(User.fromUuid(uuid)).build();
	}
	
	@GET
	@Path("/me")
	public synchronized Response me(@BeanParam User user) throws Exception
	{
		user.continueOrThrow();
		return Response.ok(user).build();
	}
	
	@POST
	@Path("/register")
	public synchronized Response register(User user) throws Exception
	{
		if (user == null)
			throw new ApiException(Constants.Strings.MISSING_CONTENT_BODY, Status.NOT_ACCEPTABLE.getStatusCode());
		user.validate(new Validation(true, true, true));
		user.save();
		UserPassword up = new UserPassword(user.password);
		up.save(user.username);
		user.createdAt = null;
		user.updatedAt = null;
		user.deletedAt = null;
		user.displayName = null;
		user.password = null;
		return Response.created(null).entity(user).build();
	}
	
	@POST
	@Path("/login")
	public synchronized Response login(User user, @HeaderParam("user-agent") String userAgent) throws Exception
	{
		if (user == null)
			throw new ApiException(Constants.Strings.MISSING_CONTENT_BODY, Status.NOT_ACCEPTABLE.getStatusCode());
		user.validate(new Validation(true, true, false));
		UserPassword sup = UserPassword.fromUsername(user.username);
		UserPassword oup = new UserPassword(user.password.getBytes(), sup.salt);
		if (!sup.equals(oup))
			throw new ApiException(Constants.Strings.INVALID_CREDENTIALS, Status.FORBIDDEN.getStatusCode());
		Session session = new Session(sup.userUuid, userAgent);
		session.save();
		return Response.ok(session).build();
	}
}
