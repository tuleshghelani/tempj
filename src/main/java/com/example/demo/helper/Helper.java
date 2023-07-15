package com.example.demo.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;

@Component
public class Helper {

    public JsonNode convertToJsonNode(ObjectMapper objectMapper, JSONObject jsonObject) {
        JsonNode resultNode = objectMapper.createObjectNode();
        try {
            resultNode = objectMapper.readTree(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultNode;
    }


    public JSONObject convertToJSONObject(ObjectMapper objectMapper, JsonNode jsonNode) {
        try {
            return new JSONObject(objectMapper.writeValueAsString(jsonNode)) {
                /**
                 * changes the value of JSONObject.map to a LinkedHashMap in order to maintain
                 * order of keys.
                 */
                @Override
                public JSONObject put(String key, Object value) throws JSONException {
                    try {
                        Field map = JSONObject.class.getDeclaredField("map");
                        map.setAccessible(true);
                        Object mapValue = map.get(this);
                        if (!(mapValue instanceof LinkedHashMap)) {
                            map.set(this, new LinkedHashMap<>());
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    return super.put(key, value);
                }
            };
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
