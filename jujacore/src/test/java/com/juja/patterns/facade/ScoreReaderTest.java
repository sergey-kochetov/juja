package com.juja.patterns.facade;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public abstract class ScoreReaderTest {

    public String getJson() {
        return "{'logs':[" +
                "{'name':'adam', score:14}," +
                "{'name':'eva', score:23}," +
                "{'name':'kain', score:124}," +
                "{'name':'adam', score:114}," +
                "{'name':'eva', score:140}," +
                "{'name':'kain', score:200}," +
                "{'name':'eva', score:49}," +
                "{'name':'adam', score:14}," +
                "]}";
    }

    protected abstract ScoreReader getScoreReade();

    @Test
    public void testGetUsers() {
        // given
        ScoreReader reader = getScoreReade();

        // when
        Set<String> users = reader.getUsers();

        // then
        assertEquals("[eva, adam, kain]", users.toString());
    }

    @Test
    public void testGetTotalScore() {
        // given
        ScoreReader reader = getScoreReade();

        // when
        int scope = reader.getTotalScore();

        // then
        assertEquals(678, scope);
    }

    @Test
    public void testGetTotalScoreForUser() {
        // given
        ScoreReader reader = getScoreReade();

        // when then
        assertEquals(142, reader.getTotalScore("adam"));
        assertEquals(212, reader.getTotalScore("eva"));
        assertEquals(324, reader.getTotalScore("kain"));
    }

}