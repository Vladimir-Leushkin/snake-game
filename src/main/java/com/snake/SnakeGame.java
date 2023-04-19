package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SnakeGame extends JFrame implements ActionListener {
    private JPanel panel;
    private JLabel label;
    private final int HEIGHT = 15;
    private final int WIDTH = 15;
    private final int IMAGE_SIZE = 50;
    private static final int GOAL = 28;
    private final Cell[][] gameField = new Cell[WIDTH][HEIGHT];
    private static ArrayList<Cell> allCells;
    private static final Random random = new Random();
    private boolean isGameStopped;
    private int score = 0;
    private Apple apple;
    private Snake snake;
    private int turnDelay;
    private Timer timer;

    public static void main(String[] args) {

        new SnakeGame();
    }

    private SnakeGame() {
        createGame();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel() {
        label = new JLabel("Welcome!");
        add(label, BorderLayout.NORTH);
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Cell cell : allCells) {
                    g.drawImage((Image) cell.box.image, cell.x * IMAGE_SIZE, cell.y * IMAGE_SIZE, this);
                }
                for (Cell c : snake.getSnakeParts()) {
                    g.drawImage((Image) c.box.image, c.x * IMAGE_SIZE, c.y * IMAGE_SIZE, this);
                }
                g.drawImage((Image) apple.box.image, apple.x * IMAGE_SIZE, apple.y * IMAGE_SIZE, this);
                Toolkit.getDefaultToolkit().sync();
            }
        };
        panel.addKeyListener(new TAdapter());
        panel.setFocusable(true);
        panel.setPreferredSize(new Dimension(WIDTH * IMAGE_SIZE, HEIGHT * IMAGE_SIZE));
        add(panel);
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Snake");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }

    private void setImages() {
        for (Box box : Box.values()) {
            box.image = getImage(box.name().toLowerCase());
        }
    }

    private Image getImage(String name) {
        String fileName = "/img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
        return icon.getImage();
    }

    private ArrayList<Cell> getAllCells() {
        allCells = new ArrayList<>();
        for (int x = 0; x < WIDTH; x++) {
            allCells.addAll(Arrays.asList(gameField[x]).subList(0, HEIGHT));
        }
        return allCells;
    }

    private void createGame() {
        for (int y = 0; y < WIDTH; y++) {
            for (int x = 0; x < HEIGHT; x++) {
                gameField[y][x] = new Cell(y, x, Box.CLOSED);
            }
        }
        turnDelay = 300;
        timer = new Timer(turnDelay, this);
        timer.start();
        score = 0;
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        isGameStopped = false;
        createNewApple();
        getAllCells();
    }

    private void createNewApple() {
        int x = random.nextInt(WIDTH);
        int y = random.nextInt(HEIGHT);
        Apple a = new Apple(x, y);
        if (snake.checkCollision(a)) {
            createNewApple();
        }
        this.apple = a;
    }

    private void gameOver() {
        for (Cell c : snake.getSnakeParts()) {
            gameField[c.x][c.y].box = Box.NOBOMB;
        }
        timer.stop();
        isGameStopped = true;
        label.setText(getMessage("You lose!  Score: " + score + ". Пробел - начать заново"));
    }

    private void win() {
        timer.stop();
        isGameStopped = true;
        label.setText(getMessage("You win!!!  Score: " + score + ". Пробел - начать заново"));
    }


    private void restart() {
        isGameStopped = false;
        score = 0;
        label.setText(getMessage("Let's play again"));
        createGame();
    }


    private String getMessage(String s) {
        return s;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        snake.move(apple);
        if (apple.isAlive == false) {
            createNewApple();
            score += 5;
            label.setText(getMessage("Score:" + score));
            turnDelay -= 10;
            timer.setDelay(turnDelay);
        }
        if (!snake.isAlive) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent key) {
            int e = key.getKeyCode();
            switch (e) {
                case KeyEvent.VK_LEFT:
                    snake.setDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    snake.setDirection(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    snake.setDirection(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    snake.setDirection(Direction.DOWN);
                    break;
                case KeyEvent.VK_SPACE:
                    if (isGameStopped) {
                        restart();
                    }
            }
            panel.repaint();
        }
    }
}

