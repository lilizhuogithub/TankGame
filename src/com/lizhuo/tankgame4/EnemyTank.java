package com.lizhuo.tankgame4;

import java.util.Vector;

/**
 *敌人坦克
 */
public class EnemyTank extends Tank implements Runnable {

    //在敌人坦克类, 使用Vector 保存多个Shot
    Vector<Shot> shots = new Vector<>();
    Boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (true) {
            //根据坦克的方向来继续移动
            switch (getDirect()) {
                case 0:
                    //让坦克保持一个方向, 走30步
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0) {
                            moveUp();
                        }
                        try {    //休眠一会,要不坦克就会瞬间移动
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000) {
                            moveRight();
                        }
                        try {    //休眠一会,要不坦克就会瞬间移动
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750) {
                            moveDown();
                        }

                        try {    //休眠一会,要不坦克就会瞬间移动
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0) {
                            moveLeft();
                        }
                        try {    //休眠一会,要不坦克就会瞬间移动
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

            }


            //然后随机改变坦克的方向
            setDirect((int)(Math.random() * 4));
            if (!isLive) {
                break;
            }

        }

    }
}
