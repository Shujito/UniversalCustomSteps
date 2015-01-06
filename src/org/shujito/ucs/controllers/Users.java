package org.shujito.ucs.controllers;

import java.net.URI;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import org.shujito.ucs.Crypto;
import org.shujito.ucs.GsonWrapper;
import org.shujito.ucs.JedisWrapper;
import org.shujito.ucs.models.AccessToken;
import org.shujito.ucs.models.PasswordSalt;
import org.shujito.ucs.models.User;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Users
{
	public static final String TAG = Users.class.getSimpleName();
	private Gson gson = GsonWrapper.getInstance().getGson();
	
	@GET
	public Response index()
	{
		Jedis jedis = JedisWrapper.open();
		List<User> usersList = new ArrayList<>();
		Map<String, String> usersMap = jedis.hgetAll("users");
		for (String entry : usersMap.values())
		{
			User user = this.getUser(jedis, entry);
			usersList.add(user);
		}
		return Response.ok(usersList).build();
	}
	
	@GET
	@Path("/{userID}")
	public Response user(@PathParam("userID") String userID)
	{
		if (userID.length() != 32)
			throw new ApiException(Status.NOT_FOUND.getReasonPhrase(), Status.NOT_FOUND.getStatusCode());
		Jedis jedis = JedisWrapper.open();
		if (!jedis.exists("user:" + userID))
			throw new ApiException(Constants.Strings.USER_DOES_NOT_EXIST, Status.FORBIDDEN.getStatusCode());
		User user = this.getUser(jedis, userID);
		return Response.ok(user).build();
	}
	
	@GET
	@Path("/me")
	public Response me(@BeanParam User user) throws Exception
	{
		user.continueOrThrow();
		user.accessToken = null;
		return Response.ok(user).build();
	}
	
	@POST
	@Path("/register")
	public Response register(User user) throws Exception
	{
		this.validateUser(user, new User.Validation(true, true, true, false));
		this.lowerFields(user);
		Jedis jedis = JedisWrapper.open();
		this.checkExisting(jedis, user);
		PasswordSalt passwordSalt = this.saltAndHash(user);
		user.id = this.saveUserData(jedis, user, passwordSalt);
		user.password = null;
		user.displayName = null;
		URI resourceUri = URI.create("users/" + user.id);
		return Response.created(resourceUri)
			.entity(user)
			.build();
	}
	
	@POST
	@Path("/login")
	public Response login(User user) throws Exception
	{
		this.validateUser(user, new User.Validation(true, true, false, false));
		this.lowerFields(user);
		Jedis jedis = JedisWrapper.open();
		this.checkUserExists(jedis, user);
		user.id = jedis.hget("users", user.username);
		this.compareCredentials(jedis, user);
		AccessToken accessToken = this.generateAccessToken();
		this.saveAccessToken(jedis, user, accessToken);
		return Response.ok(accessToken).build();
	}
	
	@DELETE
	@Path("/logout")
	public Response logout(@BeanParam User user) throws Exception
	{
		user.continueOrThrow();
		Jedis jedis = JedisWrapper.open();
		Transaction transaction = jedis.multi();
		transaction.srem("sessions:" + user.id, user.accessToken);
		transaction.del("session:" + user.accessToken);
		transaction.exec();
		return Response.accepted().build();
	}
	
	@GET
	@Path("/test_auth")
	public Response beanAuth(@BeanParam User user) throws Exception
	{
		user.continueOrThrow();
		Map<String, Object> messageMap = new HashMap<>();
		messageMap.put("success", true);
		messageMap.put("message", "Hello, " + user.displayName + "!");
		return Response.ok(messageMap).build();
	}
	
	private void validateUser(User user, User.Validation validation)
	{
		if (user == null)
			throw new ApiException(Constants.Strings.MISSING_CONTENT_BODY, Status.NOT_ACCEPTABLE.getStatusCode());
		String validationString = user.validate(validation);
		if (validationString != null)
			throw new ApiException(validationString, Status.NOT_ACCEPTABLE.getStatusCode());
	}
	
	private void lowerFields(User user)
	{
		user.displayName = user.username;
		if (user.username != null)
			user.username = user.username.toLowerCase();
		if (user.email != null)
			user.email = user.email.toLowerCase();
	}
	
	private void checkExisting(Jedis jedis, User user)
	{
		if (jedis.sismember("users:emails", user.email))
			throw new ApiException(Constants.Strings.MAIL_ALREADY_IN_USE, Status.CONFLICT.getStatusCode());
		if (jedis.hexists("users", user.username))
			throw new ApiException(Constants.Strings.USER_EXISTS, Status.CONFLICT.getStatusCode());
	}
	
	private PasswordSalt saltAndHash(User user) throws Exception
	{
		byte[] saltBytes = new byte[32];
		new SecureRandom().nextBytes(saltBytes);
		return this.saltAndHash(user, saltBytes);
	}
	
	private PasswordSalt saltAndHash(User user, String base64SaltBytes) throws Exception
	{
		byte[] saltBytes = Crypto.base64decode(base64SaltBytes);
		return this.saltAndHash(user, saltBytes);
	}
	
	private PasswordSalt saltAndHash(User user, byte[] saltBytes) throws Exception
	{
		byte[] passwordBytes = user.password.getBytes("UTF-8");
		byte[] saltedPasswordBytes = new byte[passwordBytes.length + saltBytes.length];
		for (int idx = 0; idx < saltBytes.length; idx++)
			saltedPasswordBytes[idx] = saltBytes[idx];
		for (int idx = 0; idx < passwordBytes.length; idx++)
			saltedPasswordBytes[idx + saltBytes.length] = passwordBytes[idx];
		// hash password
		byte[] sha256passwordBytes = Crypto.sha256(saltedPasswordBytes);
		// ah
		byte[] bytesContainer = new byte[sha256passwordBytes.length + saltBytes.length];
		// stretchy
		for (int idx = 0; idx < 0x7ffff; idx++)
		{
			for (int jdx = 0; jdx < saltBytes.length; jdx++)
				bytesContainer[jdx] = saltBytes[jdx];
			for (int jdx = 0; jdx < sha256passwordBytes.length; jdx++)
				bytesContainer[jdx + saltBytes.length] = sha256passwordBytes[jdx];
			sha256passwordBytes = Crypto.sha256(bytesContainer);
		}
		PasswordSalt passwordSalt = new PasswordSalt();
		passwordSalt.password = Crypto.base64encode(sha256passwordBytes);
		passwordSalt.salt = Crypto.base64encode(saltBytes);
		return passwordSalt;
	}
	
	private String saveUserData(Jedis jedis, User user, PasswordSalt passwordSalt)
	{
		String userID = UUID.randomUUID().toString().replace("-", "");
		user.created = new Date().getTime();
		Transaction transaction = jedis.multi();
		transaction.hset("user:" + userID, User.DISPLAY_NAME, user.displayName);
		transaction.hset("user:" + userID, User.USERNAME, user.username);
		transaction.hset("user:" + userID, User.EMAIL, user.email);
		transaction.hset("user:" + userID, User.CREATED, user.created.toString());
		// users index
		transaction.hset("users", user.username, userID);
		// password
		transaction.hset("users:passwords:" + userID, PasswordSalt.PASSWORD, passwordSalt.password);
		transaction.hset("users:passwords:" + userID, PasswordSalt.SALT, passwordSalt.salt);
		// emails
		transaction.sadd("users:emails", user.email);
		transaction.exec();
		return userID;
	}
	
	private void checkUserExists(Jedis jedis, User user)
	{
		// check if user exists
		if (!jedis.hexists("users", user.username))
			throw new ApiException(Constants.Strings.USER_DOES_NOT_EXIST, Status.NOT_FOUND.getStatusCode());
	}
	
	private void compareCredentials(Jedis jedis, User user) throws Exception
	{
		// compare passwords
		Map<String, String> passwordSaltMap = jedis.hgetAll("users:passwords:" + user.id);
		JsonElement passwordSaltMapJson = this.gson.toJsonTree(passwordSaltMap);
		PasswordSalt passwordSalt = this.gson.fromJson(passwordSaltMapJson, PasswordSalt.class);
		PasswordSalt comparePasswordSalt = this.saltAndHash(user, passwordSalt.salt);
		if (!passwordSalt.equals(comparePasswordSalt))
			throw new ApiException(Constants.Strings.INVALID_CREDENTIALS, Status.NOT_ACCEPTABLE.getStatusCode());
	}
	
	private AccessToken generateAccessToken() throws Exception
	{
		// generate access token
		byte[] randomBytes = new byte[16];
		new SecureRandom().nextBytes(randomBytes);
		// more entropee
		UUID uuid = UUID.randomUUID();
		long leastLong = uuid.getLeastSignificantBits();
		long mostLong = uuid.getMostSignificantBits();
		// assemble token
		byte[] tokenBytes = ByteBuffer.allocate(32)
			.put(randomBytes)
			.putLong(16, leastLong)
			.putLong(16 + 8, mostLong)
			.array();
		AccessToken accessToken = new AccessToken();
		accessToken.accessToken = tokenBytes;
		accessToken.expires = new Date().getTime() + 604800000;
		return accessToken;
	}
	
	private void saveAccessToken(Jedis jedis, User user, AccessToken accessToken) throws Exception
	{
		// TODO: strengthen
		byte[] sha256tokenBytes = Crypto.sha256(accessToken.accessToken);
		String base64sha256token = Crypto.base64encode(sha256tokenBytes);
		Transaction transaction = jedis.multi();
		// session data
		transaction.hset("session:" + base64sha256token, AccessToken.EXPIRES, accessToken.expires.toString());
		transaction.hset("session:" + base64sha256token, User.ID, user.id);
		// expire
		transaction.pexpireAt("session:" + base64sha256token, accessToken.expires);
		// add to user's sessions
		transaction.sadd("sessions:" + user.id, base64sha256token);
		transaction.exec();
	}
	
	private User getUser(Jedis jedis, String userID)
	{
		Map<String, String> userMap = jedis.hgetAll("user:" + userID);
		JsonElement userMapJson = this.gson.toJsonTree(userMap);
		User user = this.gson.fromJson(userMapJson, User.class);
		user.id = userID;
		user.email = null;
		user.username = user.displayName;
		user.displayName = null;
		return user;
	}
}
