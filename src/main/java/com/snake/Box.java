package com.snake;

public enum Box {

    BOMB,
    CLOSED,
    NOBOMB,
    FLAGED;

    public Object image;

    Box getNextNumberBox() {
        return Box.values()[this.ordinal() + 1];
    }

    public int getNumber() {
        return this.ordinal();
    }
}
