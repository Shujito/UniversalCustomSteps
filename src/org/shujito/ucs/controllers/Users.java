package org.shujito.ucs.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
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
import org.shujito.ucs.db.Database;
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
		try (Statement smt = Database.createStatement())
		{
			try (ResultSet rs = smt.executeQuery("select uuid,created_at,display_name as username from users where deleted_at is null order by username asc"))
			{
				List<User> users = new ArrayList<>();
				while (rs.next())
				{
					User user = User.fromResultSet(rs);
					users.add(user);
				}
				return Response.ok(users).build();
			}
		}
	}
	
	@GET
	@Path("{uuid}")
	public synchronized Response index(@PathParam("uuid") String uuid) throws Exception
	{
		try (PreparedStatement smt = Database.prepareStatement("select created_at,display_name as username from users where uuid = ? and deleted_at is null"))
		{
			smt.setString(1, uuid);
			try (ResultSet rs = smt.executeQuery())
			{
				if (rs.next())
				{
					User user = User.fromResultSet(rs);
					return Response.ok(user).build();
				}
			}
		}
		throw new ApiException(Constants.Strings.USER_DOES_NOT_EXIST, Status.NOT_FOUND.getStatusCode());
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
		try (PreparedStatement psm = Database.prepareStatement("select "
			+ "users.uuid as user_uuid,"
			+ "user_passwords.password as password,"
			+ "user_passwords.salt as salt"
			+ " from users"
			+ " inner join user_passwords"
			+ " on users.uuid=user_passwords.user_uuid"
			+ " where users.username=lower(?)"))
		{
			psm.setString(1, user.username);
			try (ResultSet rs = psm.executeQuery())
			{
				if (!rs.next())
					throw new ApiException(Constants.Strings.USER_DOES_NOT_EXIST, Status.NOT_FOUND.getStatusCode());
				UserPassword sup = UserPassword.fromResultSet(rs);
				UserPassword oup = new UserPassword(user.password.getBytes(), sup.salt);
				//oup.hashPassword();
				if (!sup.equals(oup))
					throw new ApiException(Constants.Strings.INVALID_CREDENTIALS, Status.FORBIDDEN.getStatusCode());
				Session session = new Session(sup.userUuid, userAgent);
				session.save();
				return Response.ok(session).build();
			}
		}
	}
}
