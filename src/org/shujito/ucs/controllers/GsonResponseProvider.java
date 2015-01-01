package org.shujito.ucs.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.shujito.ucs.GsonWrapper;

import com.google.gson.Gson;

/**
 * <p>Handles and converts POJO's to JSON using {@link Gson}</p>
 * <p>See here: <a href="http://eclipsesource.com/blogs/2012/11/02/integrating-gson-into-a-jax-rs-based-application/">http://eclipsesource.com/blogs/2012/11/02/integrating-gson-into-a-jax-rs-based-application/</a>
 * </p>
 * @author not shujito
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GsonResponseProvider
	implements
	MessageBodyReader<Object>,
	MessageBodyWriter<Object>
{
	public static final String TAG = GsonResponseProvider.class.getSimpleName();
	private static final String UTF_8 = "UTF-8";
	private Gson gson = GsonWrapper.getInstance().getGson();
	
	/* MessageBodyReader<Object> */
	@Override
	public boolean isReadable(Class<?> type,
		Type genericType,
		Annotation[] annotations,
		MediaType mediaType)
	{
		return true;
	}
	
	@Override
	public Object readFrom(Class<Object> type,
		Type genericType,
		Annotation[] annotations,
		MediaType mediaType,
		MultivaluedMap<String, String> httpHeaders,
		InputStream entityStream)
		throws
		IOException,
		WebApplicationException
	{
		InputStreamReader streamReader = new InputStreamReader(entityStream, UTF_8);
		try
		{
			Type jsonType;
			if (type.equals(genericType))
			{
				jsonType = type;
			}
			else
			{
				jsonType = genericType;
			}
			try
			{
				return this.gson.fromJson(streamReader, jsonType);
			}
			catch (Exception ex)
			{
				return null;
			}
		}
		finally
		{
			streamReader.close();
		}
	}
	
	/* MessageBodyWriter<Object> */
	@Override
	public boolean isWriteable(Class<?> type,
		Type genericType,
		Annotation[] annotations,
		MediaType mediaType)
	{
		return true;
	}
	
	@Override
	public long getSize(Object t,
		Class<?> type,
		Type genericType,
		Annotation[] annotations,
		MediaType mediaType)
	{
		return -1;
	}
	
	@Override
	public void writeTo(Object t,
		Class<?> type,
		Type genericType,
		Annotation[] annotations,
		MediaType mediaType,
		MultivaluedMap<String, Object> httpHeaders,
		OutputStream entityStream)
		throws
		IOException,
		WebApplicationException
	{
		OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8);
		try
		{
			Type jsonType;
			if (type.equals(genericType))
			{
				jsonType = type;
			}
			else
			{
				jsonType = genericType;
			}
			this.gson.toJson(t, jsonType, writer);
		}
		finally
		{
			writer.close();
		}
	}
}
