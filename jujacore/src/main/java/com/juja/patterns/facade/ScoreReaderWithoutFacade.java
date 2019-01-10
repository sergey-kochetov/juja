package com.juja.patterns.facade;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

// это наш клиент, он работает с json но через но не с удобным JSONObject
public class ScoreReaderWithoutFacade implements ScoreReader {
    private JSONObject json;

    public ScoreReaderWithoutFacade(String jsonString) {
        this.json = new JSONObject(jsonString);
    }

    @Override
    public Set<String> getUsers() {
        Set<String> result = new HashSet<>();
        JSONArray array = json.getJSONArray("logs");
        for (Object object : array) {
            validateObject(object);
            JSONObject log = (JSONObject) object;
            result.add(log.getString("name"));
        }
        return result;
    }

    @Override
    public int getTotalScore(String userName) {
        int score = 0;
        JSONArray array = json.getJSONArray("logs");
        for (Object object : array) {
            validateObject(object);
            JSONObject log = (JSONObject) object;
            if (log.getString("name").equals(userName)) {
                score += log.getInt("score");
            }
        }
        return score;
    }

    @Override
    public int getTotalScore() {
        int score = 0;
        JSONArray array = json.getJSONArray("logs");
        for (Object object : array) {
            validateObject(object);
            JSONObject log = (JSONObject) object;
            score += log.getInt("score");
        }
        return score;
    }

    private void validateObject(Object object) {
        if (!(object instanceof JSONObject)) {
            throw new IllegalArgumentException("Bad format. " +
                    "Expected: JSONObject, but was: " +
                    object.getClass().getSimpleName());
        }

    }
}
