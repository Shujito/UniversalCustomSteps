package org.shujito.ucs.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
import org.shujito.ucs.models.User;
import org.shujito.ucs.models.UserPassword;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Users
{
	public static final String TAG = Users.class.getSimpleName();
	
	@GET
	public Response index() throws Exception
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
	public Response index(@PathParam("uuid") String uuid) throws Exception
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
	public Response register(User user) throws Exception
	{
		//System.out.println(this.gson.toJson(user));
		//throw new ApiException("Not yet", Status.NOT_FOUND.getStatusCode());
		user.validate();
		user.save();
		UserPassword up = new UserPassword(user.password);
		up.user = user.username.toLowerCase();
		up.save();
		user.createdAt = null;
		user.updatedAt = null;
		user.deletedAt = null;
		user.displayName = null;
		user.password = null;
		return Response.created(null).entity(user).build();
	}
}
