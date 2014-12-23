package org.shujito.ucs;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.shujito.ucs.models.ApiResponse;

import com.google.gson.Gson;

public class ApiErrorHandler extends ErrorHandler
{
	public static final String TAG = ApiErrorHandler.class.getSimpleName();
	private Gson gson = GsonWrapper.getInstance().getGson();
	
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.success = false;
		apiResponse.status = response.getStatus();
		apiResponse.message = (response instanceof Response) ? ((Response) response).getReason() : null;
		baseRequest.setHandled(true);
		response.setHeader("content-type", MediaType.APPLICATION_JSON);
		response.getWriter().write(this.gson.toJson(apiResponse));
	}
}
