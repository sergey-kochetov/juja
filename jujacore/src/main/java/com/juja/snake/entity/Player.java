package com.juja.snake.entity;

import com.juja.snake.engine.RenderObject;
import com.juja.snake.game.Game;
import com.juja.snake.manager.KeyCheckManager;
import javafx.geometry.Side;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends RenderObject {

    private Integer x;
    private Integer y;
    private HashMap<Integer, Dot> dots;
    private Side moveDir = Side.RIGHT;
    private Boolean delay = false;
    private ArrayList<Side> moveHistory;
    private Boolean death = false;
    private Integer score;
    private final Game game;

    public Player(Game game, Integer x, Integer y) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.moveHistory = new ArrayList<>();
        this.score = 0;

        dots = new HashMap<>();
        moveHistory.add(moveDir);
        dots.put(0, new Dot(game, x, y, 0));
        growUp(false);
        growUp(false);
    }

    @Override
    public void draw(Graphics g) {
        for (Dot dot : dots.values()) {
            dot.draw(g);
        }
    }

    private void checkKeys() {
        if (death) return;
        if (KeyCheckManager.keysCheck(KeyEvent.VK_W)) {
            if (moveDir != Side.BOTTOM) {
                moveDir = Side.TOP;
            }
        } else if (KeyCheckManager.keysCheck(KeyEvent.VK_S)) {
            if (moveDir != Side.TOP) {
                moveDir = Side.BOTTOM;
            }
        } else if (KeyCheckManager.keysCheck(KeyEvent.VK_A)) {
            if (moveDir != Side.RIGHT) {
                moveDir = Side.LEFT;
            }
        } else if (KeyCheckManager.keysCheck(KeyEvent.VK_D)) {
            if (moveDir != Side.LEFT) {
                moveDir = Side.RIGHT;
            }
        } else if (KeyCheckManager.keysCheck(KeyEvent.VK_E)) {
            growUp(true);
        }
    }

    @Override
    public void update() {
        if (death) return;
        Integer moveX = 0;
        Integer moveY = 0;

        checkKeys();

        if (!delay) {
            switch (moveDir) {
                case TOP:
                    moveY = -40;
                    break;
                case BOTTOM:
                    moveY = 40;
                    break;
                case RIGHT:
                    moveX = 40;
                    break;
                case LEFT:
                    moveX = -40;
                    break;
            }

            delay = true;

            moveHistory.add(moveDir);

            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    delay = false;
                }
            }).start();

            dots.get(0).updateMovement(moveX, moveY);

            x = dots.get(0).getX();
            y = dots.get(0).getY();

            for (Dot dot : dots.values()) {
                if (dot.getKey() != 0) {

                    Integer gettingKey = dot.getKey();
                    Side side = getLastMove(gettingKey);

                    dot.updateMovement(getMovementBySide(side)[0], getMovementBySide(side)[1]);
                }
            }

            if (game.getFoodManager().checkCollision(dots.get(0).getX(), dots.get(0).getY())) {

                growUp(true);

            }
        }
    }

    public Side getLastMove(Integer key) {

        Integer finalKey;

        while ((finalKey = moveHistory.size() - 1 - key) < 0) {
            key--;
        }

        try {
            return moveHistory.get(finalKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void growUp(Boolean updateScore) {
        if (dots.get(dots.size()) == null) {

            Dot dot = dots.get(dots.size() - 1);
            Side side = getLastMove(dot.getKey());

            dots.put(dots.size(), new Dot(game, dot.getX() - getMovementBySide(side)[0], dot.getY() - getMovementBySide(side)[1], dots.size()));

            if (updateScore) {
                this.score += 100;
            }
        }
    }


    public void die() {
        this.death = true;
        game.getFoodManager().stop();
        game.getEndScreen().show();
    }

    private Integer[] getMovementBySide(Side side) {
        switch (side) {
            case TOP:
                return new Integer[]{0, -40};
            case BOTTOM:
                return new Integer[]{0, 40};
            case RIGHT:
                return new Integer[]{40, 0};
            case LEFT:
                return new Integer[]{-40, 0};
        }
        return null;
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
        return null;
    }

    @Override
    public Integer getHeight() {
        return null;
    }

    public Integer getScore() {
        return this.score;
    }

    public HashMap<Integer, Dot> getDots() {
        return dots;
    }
}