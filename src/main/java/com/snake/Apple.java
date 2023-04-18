package com.snake;


public class Apple extends Cell {

    //private static final String APPLE_SIGN = "\uD83C\uDF4E";

    public boolean isAlive = true;

    public Apple(int x, int y, Box box) {

        super(x, y, box);
    }

    public void draw() {
        Box box = isAlive ? Box.FLAGED : null;
        this.box = box;
    }
}
