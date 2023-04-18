package com.snake;


public class Apple extends Cell {

    public boolean isAlive = true;

    public Apple(int x, int y) {
        super(x, y);
        this.box = Box.FLAGED;
    }
}
