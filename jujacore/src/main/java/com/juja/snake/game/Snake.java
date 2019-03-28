package com.juja.snake.game;

public class Snake {

    private static Game game;

    public static void main(String[] args) {
        game = new Game();
    }

    public Game getGame() {
        return game;
    }
}