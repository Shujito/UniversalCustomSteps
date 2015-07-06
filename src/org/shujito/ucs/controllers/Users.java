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

/**
 * Register, login, list and do stuff with users here.
 * @author shujito
 */
@Path("/users")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Users
{
	public static final String TAG = Users.class.getSimpleName();
	
	/**
	 * List users
	 * @return
	 * @throws Exception
	 */
	@GET
	public synchronized Response index() throws Exception
	{
		return Response.ok(User.getAll()).build();
	}
	
	/**
	 * Find a user by its uuid
	 * @param uuid of the user to look for
	 * @return the user object
	 * @throws Exception
	 */
	@GET
	@Path("{uuid}")
	public synchronized Response index(@PathParam("uuid") String uuid) throws Exception
	{
		return Response.ok(User.fromUuid(uuid)).build();
	}
	
	/**
	 * Get the current user info
	 * @param user
	 * @return a user object
	 * @throws Exception
	 */
	@GET
	@Path("/me")
	public synchronized Response me(@BeanParam User user) throws Exception
	{
		user.continueOrThrow();
		return Response.ok(user).build();
	}
	
	/**
	 * Register a new user
	 * @param user data comes from POST request body
	 * @return a 201 response with the created user info
	 * @throws Exception
	 */
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
	
	/**
	 * Logs a user in
	 * @param user data comes from POST request body
	 * @param userAgent
	 * @return a response with a {@link Session} object
	 * @throws Exception
	 */
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
