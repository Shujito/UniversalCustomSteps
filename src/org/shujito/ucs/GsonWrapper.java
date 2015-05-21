package org.shujito.ucs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

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
			return new JsonPrimitive(Crypto.base64encode(src));
		}
		
		@Override
		public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return Crypto.base64decode(json.getAsString());
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
		ExcludeFieldsWithoutSerializedName efwosn = new ExcludeFieldsWithoutSerializedName();
		this.mGson = new GsonBuilder()
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
			//.disableHtmlEscaping()
			.addDeserializationExclusionStrategy(efwosn)
			.addSerializationExclusionStrategy(efwosn)
			.registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64Serializer())
			.create();
	}
	
	public Gson getGson()
	{
		return this.mGson;
	}
}
