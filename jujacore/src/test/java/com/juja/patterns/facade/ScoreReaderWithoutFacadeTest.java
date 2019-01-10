package com.juja.patterns.facade;

import static org.junit.Assert.*;

public class ScoreReaderWithoutFacadeTest extends ScoreReaderTest {
    @Override
    protected ScoreReader getScoreReade() {
        return new ScoreReaderWithoutFacade(getJson());
    }
}