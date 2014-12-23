package org.shujito.ucs.controllers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.shujito.ucs.ApiException;

@Provider
public class ApiExceptionHandler implements ExceptionMapper<ApiException>
{
	public static final String TAG = ApiExceptionHandler.class.getSimpleName();
	
	@Override
	public Response toResponse(ApiException exception)
	{
		return Response.status(exception.response.status).entity(exception.response).build();
	}
}
