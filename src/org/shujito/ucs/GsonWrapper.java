package org.shujito.ucs;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonWrapper
{
	public static final String TAG = GsonWrapper.class.getSimpleName();
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
		this.mGson = new GsonBuilder()
			.create();
	}
	
	public Gson getGson()
	{
		return this.mGson;
	}
}
