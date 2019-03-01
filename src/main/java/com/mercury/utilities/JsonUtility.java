package com.mercury.utilities;

import com.google.gson.Gson;

public class JsonUtility {

	public static String objectToJson(Object object)   {
        return new Gson().toJson(object);
	}
	
}
