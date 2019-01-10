package com.juja.patterns.facade;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// это наш клиент, он работает с json но через но не с удобным JSONObject
public class ScoreReaderWithFacade implements ScoreReader {
    private JsonObjectFacade json;

    public ScoreReaderWithFacade(String jsonString) {
        this.json = new JsonObjectFacade(jsonString);
    }

    @Override
    public Set<String> getUsers() {
        List<String> elements = json.getElements(String.class,
                "logs", "name", null, null);
        return new HashSet<>(elements);
    }

    @Override
    public int getTotalScore(String userName) {
        List<Integer> elements = json.getElements(Integer.class,
                "logs", "score", "name", userName);
        int result = 0;
        for (Integer score : elements) {
            result += score;
        }
        return result;
    }

    @Override
    public int getTotalScore() {
        List<Integer> elements = json.getElements(Integer.class,
                "logs", "score", null, null);
        int result = 0;
        for (Integer score : elements) {
            result += score;
        }
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
