package com.juja.patterns.facade;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JsonObjectFacade {
    private JSONObject json;

    public JsonObjectFacade(String json) {
        this.json = new JSONObject(json);
    }
    public <T> List<T> getElements(Class<T> aClass, String arrayName,
                                   String propertyName,
                                   String filterPropertyName,
                                   String filterPropertyValue) {
        List<T> result = new LinkedList<>();
        JSONArray array = json.getJSONArray(arrayName);
        for (Object object : array) {
            validateObject(object);
            JSONObject log = (JSONObject) object;
            if (filterPropertyName == null || log.getString(filterPropertyName).equals(filterPropertyValue)) {
                result.add((T) log.get(propertyName));
            }        }
        return result;
    }

    private void validateObject(Object object) {
        if (!(object instanceof JSONObject)) {
            throw new IllegalArgumentException("Bad format. " +
                    "Expected: JSONObject, but was: " +
                    object.getClass().getSimpleName());
        }

    }
}
