package com.snake;

public class Cell {

    public int x;
    public int y;

    public Box box;

    public Cell(int x, int y, Box box) {
        this.x = x;
        this.y = y;
        this.box = box;
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
