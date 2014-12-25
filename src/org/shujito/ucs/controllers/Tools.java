package org.shujito.ucs.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("tools")
@Produces(MediaType.APPLICATION_JSON)
public class Tools
{
	public static final String TAG = Tools.class.getSimpleName();
	
	@GET
	@Path("user_agent")
	public Response userAgent(@Context HttpServletRequest request, @HeaderParam("user-agent") String userAgent)
	{
		Map<String, String> map = new HashMap<>();
		map.put("user-agent", userAgent);
		map.put("ip", request.getRemoteAddr());
		return Response.ok(map).build();
	}
}
