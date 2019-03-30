package com.juja.snake.object;

import com.juja.snake.engine.RenderObject;
import com.juja.snake.game.Game;

import java.awt.*;

public class ScoreScreen extends RenderObject {

    private final Integer x = 0;
    private final Integer y = 0;
    private final Integer width = Game.getGameWidth();
    private final Integer height = Game.getGameHeight();
    private final Game game;

    public ScoreScreen(Game game) {
        this.game = game;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        g.drawString("Score: " + game.getPlayer().getScore(), 20, 30);
    }

    @Override
    public void update() {

    }

    @Override
    public Integer getX() {
        return x;
    }


    @Override
    public Integer getY() {
        return y;
    }


    @Override
    public Integer getWidth() {
        return width;
    }


    @Override
    public Integer getHeight() {
        return height;
    }

}
