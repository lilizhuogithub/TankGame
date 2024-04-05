package com.lizhuo.tankgame1;

import javax.swing.*;
import java.awt.*;

/**
 * 坦克大战的绘图区域（画板）
 */
public class MyPanel extends JPanel {
    //定义我的坦克
    Hero hero = null;

    public MyPanel() {
        this.hero = new Hero(100, 100);    //初始化自己的坦克
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);    //填充矩形，默认是黑色
        //画出坦克-封装方法
        drawTank(hero.getX(), hero.getY(), g, 0, 0);
    }

    //编写方法，画出坦克
    /**
     * @param x      坦克的左上角x坐标
     * @param y      坦克的左上角y坐标
     * @param g      画笔
     * @param direct 坦克的方向
     * @param type   坦克类型 (用颜色标记我的坦克，还是敌人的坦克)
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0:    //我方坦克
                g.setColor(Color.cyan);
                break;
            case 1:    //敌方坦克
                g.setColor(Color.yellow);
                break;
        }

        switch (direct) {
            case 0:    //默认向上
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//画出炮筒
            default:
                System.out.println("暂时不处理");

        }
    }
}
