package org.shujito.ucs.controllers;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.shujito.ucs.GsonWrapper;
import org.shujito.ucs.db.Database;
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
	public Response index() throws Exception
	{
		try (Statement smt = Database.createStatement())
		{
			try (ResultSet rs = smt.executeQuery("select uuid,username,display_name,created_at from users where deleted_at is null"))
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
	
	@POST
	@Path("/register")
	public Response register(User user) throws Exception
	{
		System.out.println(this.gson.toJson(user));
		//throw new ApiException("Not yet", Status.NOT_FOUND.getStatusCode());
		user.validate();
		user.save();
		user.createdAt = null;
		user.updatedAt = null;
		user.deletedAt = null;
		user.displayName = null;
		user.password = null;
		return Response.ok(user).build();
	}
}
