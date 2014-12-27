package org.shujito.ucs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class GsonWrapper
{
	public static final String TAG = GsonWrapper.class.getSimpleName();
	
	private static final class ByteArrayToBase64Serializer
		implements
		JsonSerializer<byte[]>,
		JsonDeserializer<byte[]>
	{
		@Override
		public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context)
		{
			return new JsonPrimitive(Base64.encode(src));
		}
		
		@Override
		public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			try
			{
				return Base64.decode(json.getAsString());
			}
			catch (Base64DecodingException ex)
			{
				throw new RuntimeException(ex);
			}
		}
	}
	
	private static class ExcludeFieldsWithoutSerializedName implements ExclusionStrategy
	{
		@Override
		public boolean shouldSkipClass(Class<?> clss)
		{
			return false;
		}
		
		@Override
		public boolean shouldSkipField(FieldAttributes fieldAttributes)
		{
			// get all annotations, skip these without @SerializedName
			Collection<Annotation> annotations = fieldAttributes.getAnnotations();
			for (Annotation a : annotations)
			{
				if (a instanceof SerializedName)
				{
					return false;
				}
			}
			return true;
		}
	}
	
	public static final TypeToken<Map<String, String>> stringMapTypeToken = new TypeToken<Map<String, String>>() {};
	private static GsonWrapper instance = null;
	
	public static GsonWrapper getInstance()
	{
		if (instance == null)
			instance = new GsonWrapper();
		return instance;
	}
	
	private final Gson mGson;
	
	private GsonWrapper()
	{
		if (instance != null)
			throw new RuntimeException();
		instance = this;
		ExcludeFieldsWithoutSerializedName sfwosn = new ExcludeFieldsWithoutSerializedName();
		this.mGson = new GsonBuilder()
			.addDeserializationExclusionStrategy(sfwosn)
			.addSerializationExclusionStrategy(sfwosn)
			.registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64Serializer())
			.create();
	}
	
	public Gson getGson()
	{
		return this.mGson;
	}
}
