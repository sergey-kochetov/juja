package com.juja.snake.engine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Display extends JComponent {

    private static final long serialVersionUID = 1L;
    private ArrayList<RenderObject> renderlist;

    public Display() {
        renderlist = new ArrayList<>();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (RenderObject renderObject : renderlist) {
            renderObject.draw(g);
        }
    }

    public void addToRenderList(RenderObject renderObject) {
        renderlist.add(renderObject);
    }

    public void removeFromRenderList(RenderObject renderObject) {
        if (renderlist.contains(renderObject)) {
            renderlist.remove(renderObject);
        }
    }

    public ArrayList<RenderObject> getRenderlist() {
        return renderlist;
    }

}