package com.snake;


import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<Cell> snakeParts = new ArrayList<>();
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;


    public Snake(int x, int y) {
        snakeParts.add(new Cell(x, y, Box.BOMB));
        snakeParts.add(new Cell(x + 1, y, Box.BOMB));
        snakeParts.add(new Cell(x + 2, y, Box.BOMB));
    }

    public void move(Apple apple) {
        Cell snakeHead = createNewHead();
        if (checkCollision(snakeHead) || snakeHead.x > 14
                || snakeHead.x < 0 || snakeHead.y > 14 || snakeHead.y < 0) {
            isAlive = false;
            return;
        }
        if (snakeHead.x == apple.x && snakeHead.y == apple.y) {
            apple.isAlive = false;
        } else {
            removeTail();
        }
        snakeParts.add(0, snakeHead);
    }

    public Cell createNewHead() {
        Cell oldHead = snakeParts.get(0);
        if (direction == Direction.LEFT) {
            return new Cell(oldHead.x - 1, oldHead.y, Box.BOMB);
        } else if (direction == Direction.RIGHT) {
            return new Cell(oldHead.x + 1, oldHead.y, Box.BOMB);
        } else if (direction == Direction.UP) {
            return new Cell(oldHead.x, oldHead.y - 1, Box.BOMB);
        } else {
            return new Cell(oldHead.x, oldHead.y + 1, Box.BOMB);
        }
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public void setDirection(Direction dir) {

        if (direction == Direction.LEFT) {
            if (snakeParts.get(0).x != snakeParts.get(1).x && dir != Direction.RIGHT) {
                direction = dir;
            }
        }
        if (direction == Direction.RIGHT) {
            if (snakeParts.get(0).x != snakeParts.get(1).x && dir != Direction.LEFT) {
                direction = dir;
            }
        }
        if (direction == Direction.UP) {
            if (snakeParts.get(0).y != snakeParts.get(1).y && dir != Direction.DOWN) {
                direction = dir;
            }
        }
        if (direction == Direction.DOWN) {
            if (snakeParts.get(0).y != snakeParts.get(1).y && dir != Direction.UP) {
                direction = dir;
            }
        }
    }

    public boolean checkCollision(Cell cell) {
        for (Cell o : snakeParts) {
            if (o.x == cell.x && o.y == cell.y) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }

    public List<Cell> getSnakeParts() {
        return snakeParts;
    }
}

