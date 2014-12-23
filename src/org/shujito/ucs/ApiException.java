package org.shujito.ucs;

import javax.ws.rs.WebApplicationException;

import org.shujito.ucs.models.ApiResponse;

public class ApiException extends WebApplicationException
{
	private static final long serialVersionUID = 1L;
	public static final String TAG = ApiException.class.getSimpleName();
	public final ApiResponse response;
	
	public ApiException(String message, int status)
	{
		super(message, status);
		this.response = new ApiResponse();
		this.response.success = false;
		this.response.message = message;
		this.response.status = status;
	}
}
