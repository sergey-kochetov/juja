package com.juja.snake.game;

import com.juja.snake.engine.Display;
import com.juja.snake.engine.RenderObject;
import com.juja.snake.entity.Player;
import com.juja.snake.manager.FoodManager;
import com.juja.snake.manager.KeyCheckManager;
import com.juja.snake.object.DebugScreen;
import com.juja.snake.object.EndScreen;
import com.juja.snake.object.ScoreScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Game extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final String title = "Snake";
    private static final String version = "ALPHA v1.1";
    private static final Integer width = 1280;
    private static final Integer height = 720;
    private static Boolean running = false;
    private Thread thread;
    private Insets border;
    private Display display;
    private Player player;
    private FoodManager foodManager;
    private DebugScreen debugScreen;
    private ScoreScreen scoreScreen;
    private EndScreen endScreen;
    private ArrayList<RenderObject> removeList;
    private ArrayList<RenderObject> addList;

    public Game() {
        setSize(width, height);
        setVisible(true);
        this.border = getInsets();
        setVisible(false);
        setSize(width + border.right + border.left - 10, height + border.bottom + border.top - 10);
        setTitle(title + " " + version);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocation(100, 100);

        initMenu();
    }

    private void initMenu() {

        Font f = new Font("Tahoma", Font.BOLD, 100);

        JLabel l = new JLabel(title);
        JLabel l2 = new JLabel(version);
        JButton b = new JButton("Start Game");
        JButton b2 = new JButton("Credits");
        JButton b3 = new JButton("Close Game");

        l.setFont(f);
        l.setBounds((width / 2) - 155, 100, 400, 100);
        l2.setBounds(width - 100, height - 50, 100, 50);

        b.setBounds((width / 2) - 200, 300, 400, 40);
        b.setBackground(new Color(212, 212, 212));
        b2.setBounds((width / 2) - 200, 400, 400, 40);
        b2.setBackground(new Color(212, 212, 212));
        b3.setBounds((width / 2) - 200, 500, 400, 40);
        b3.setBackground(new Color(212, 212, 212));

        add(l);
        add(l2);
        add(b);
        add(b2);
        add(b3);

        b.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                remove(b);
                remove(b2);
                remove(b3);
                remove(l);
                remove(l2);
                initGame();
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

        b3.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

        setVisible(true);
    }

    private void initGame() {

        setFocusable(true);
        requestFocus();
        addKeyListener(new KeyCheckManager());

        removeList = new ArrayList<>();
        addList = new ArrayList<>();

        display = new Display();
        display.setBounds(0, 0, width, height);
        display.setFocusable(true);
        display.addKeyListener(new KeyCheckManager());

        add(display);

        player = new Player(this, 400, 400);

        foodManager = new FoodManager(this, 2000, 3);
        foodManager.start();

        debugScreen = new DebugScreen(this);
        endScreen = new EndScreen(this);
        scoreScreen = new ScoreScreen(this);

        display.addToRenderList(player);
        display.addToRenderList(debugScreen);
        display.addToRenderList(endScreen);
        display.addToRenderList(scoreScreen);

        startMainLoop();
    }

    private void startMainLoop() {
        setVisible(true);
        running = true;

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (running) {
                    try {
                        Thread.sleep(15);
                        for (RenderObject renderObject : display.getRenderlist()) {
                            renderObject.update();
                        }
                        repaint();

                        for (RenderObject renderObject : removeList) {
                            display.removeFromRenderList(renderObject);
                        }
                        removeList.clear();

                        for (RenderObject renderObject : addList) {
                            display.addToRenderList(renderObject);
                        }
                        addList.clear();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        running = false;
                    }
                }
            }
        });
        thread.start();
    }

    public void addToRemoveList(RenderObject renderObject) {
        removeList.add(renderObject);
    }

    public void addToAddList(RenderObject renderObject) {
        addList.add(renderObject);
    }

    public static Integer getGameWidth() {
        return width;
    }

    public static Integer getGameHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public Display getDisplay() {
        return display;
    }

    public FoodManager getFoodManager() {
        return foodManager;
    }

    public DebugScreen getDebugScreen() {
        return debugScreen;
    }

    public EndScreen getEndScreen() {
        return endScreen;
    }
}