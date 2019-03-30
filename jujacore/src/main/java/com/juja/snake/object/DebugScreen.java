package com.juja.snake.object;

import com.juja.snake.engine.RenderObject;
import com.juja.snake.game.Game;
import com.juja.snake.manager.KeyCheckManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class DebugScreen extends RenderObject {

    private final Integer x = 0;
    private final Integer y = 0;
    private final Integer width = Game.getGameWidth();
    private final Integer height = Game.getGameHeight();
    private final Game game;

    public DebugScreen(Game game) {
        this.game = game;
    }

    @Override
    public void draw(Graphics g) {
        if (KeyCheckManager.keysCheck(KeyEvent.VK_F)) {
            g.setColor(Color.black);
            g.drawString("Debug Screen", 10, 20);
            g.drawString("Player: [X:" + game.getPlayer().getX() + "] [Y:" + game.getPlayer().getY() + "]", 10, 40);
            g.drawString("Player length: " + game.getPlayer().getDots().size(), 10, 60);
            g.drawString("SpawnedFood: " + game.getFoodManager().getSpawnedFood(), 10, 80);
        }
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
