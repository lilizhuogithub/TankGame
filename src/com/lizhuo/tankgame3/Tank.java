package com.lizhuo.tankgame3;

/**
 *
 */

public class Tank {
    private int x;    //坦克的横坐标
    private int y;    //坦克的纵坐标
    private int direct;    //坦克方向
    private int speed = 1;    //坦克速度

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;

    }

    //增加坦克的移动方法
    public void moveUp() {
        this.y -= speed;
    }

    public void moveRight() {
        this.x += speed;
    }

    public void moveDown() {
        this.y += speed;
    }

    public void moveLeft() {
        this.x -= speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}


