package com.juja.snake.entity;

import com.juja.snake.engine.RenderObject;
import com.juja.snake.game.Game;

import java.awt.*;

public class Food extends RenderObject {


    private Integer x;
    private Integer y;
    private static final Integer width = 40;
    private static final Integer height = 40;
    private Game game;

    public Food(Game game, Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.game = game;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillRoundRect(x, y, width, height, 30, 30);
    }

    public void delete() {
        game.getFoodManager().getSpawnedFoodList().remove(this);
        game.addToRemoveList(this);
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