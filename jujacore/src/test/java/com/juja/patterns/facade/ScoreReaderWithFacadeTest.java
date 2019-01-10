package com.juja.patterns.facade;

import static org.junit.Assert.*;

public class ScoreReaderWithFacadeTest extends ScoreReaderTest {

    @Override
    protected ScoreReader getScoreReade() {
        return new ScoreReaderWithFacade(getJson());
    }
}