package com.sar.fsdemo.business.mode.serviceapi;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * @author Mr.Wang
 * @version 1.0
 * @time 2016/8/22-13:48
 * @describe
 */
public class DeserializerData implements JsonDeserializer {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.toString();
    }
}
