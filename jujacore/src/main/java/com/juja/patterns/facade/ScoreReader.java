package com.juja.patterns.facade;

import java.util.Set;

public interface ScoreReader {
    Set<String> getUsers();

    int getTotalScore(String userName);

    int getTotalScore();
}
