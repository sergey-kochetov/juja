package com.juja.snake.object;

import com.juja.snake.engine.RenderObject;
import com.juja.snake.game.Game;

import java.awt.*;

public class EndScreen extends RenderObject {

    private final Integer x = 0;
    private final Integer y = 0;
    private final Integer width = Game.getGameWidth();
    private final Integer height = Game.getGameHeight();
    private final Game game;
    private boolean visibility;

    public EndScreen(Game game) {
        this.game = game;
        this.visibility = false;
    }

    @Override
    public void draw(Graphics g) {
        if (visibility) {
            g.setColor(Color.black);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 150));
            g.drawString("Game Over", Game.getGameWidth() / 2 - 350, Game.getGameHeight() / 2);
        }
    }

    @Override
    public void update() {

    }

    public void show() {

        this.visibility = true;

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
