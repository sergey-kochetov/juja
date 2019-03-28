package com.juja.snake.engine;

import java.awt.*;

public abstract class RenderObject {

    public abstract Integer getX();

    public abstract Integer getY();

    public abstract Integer getWidth();

    public abstract Integer getHeight();

    public abstract void draw(Graphics g);

    public abstract void update();
}