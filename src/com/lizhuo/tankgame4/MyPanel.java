package com.lizhuo.tankgame4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * 坦克大战的绘图区域（画板）
 */
//为了响应键盘事件，实现keyListener
//为了让Panel 不停的重绘子弹, 需要将 MyPanel 实现 Runnable 接口, 当作一个线程使用
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;
    //定义敌人坦克, 方到Vector中为了线程安全
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTanksSize = 3;    //初始敌人坦克数量

    public MyPanel() {
        this.hero = new Hero(100, 100);    //初始化自己的坦克
        hero.setSpeed(1);    //设置坦克速度, 默认是1;
        //初始化敌人坦克
        for (int i = 0; i < enemyTanksSize; i++) {
            //创建一个敌人坦克
            EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
            //设置方向
            enemyTank.setDirect(2);
            //给该enemyTank加入一颗子弹
            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
            //加入enemyTank的Vector成员
            enemyTank.shots.add(shot);
            //启动shot对象
            new Thread(shot).start();
            //加入
            enemyTanks.add(enemyTank);
        }
    }

    @Override
    @SuppressWarnings("all")
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);    //填充矩形，默认是黑色
        //画出坦克-封装方法
        drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);    //画出我方坦克
        for (EnemyTank enemyTank : enemyTanks) {    //画出敌人坦克
            drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
            //画出 enemyTank 所有的子弹
            for (int i = 0; i < enemyTank.shots.size(); i++){
                //取出子弹
                Shot shot = enemyTank.shots.get(i);
                //绘制
                if (shot.isLive) {
                    g.draw3DRect(shot.x, shot.y, 1, 1, false);
                } else {
                    //从Vector移除
                    enemyTank.shots.remove(shot);
                }
            }
        }

        //画出hero射击的子弹
        if (hero.shot != null && hero.shot.isLive == true) {
            g.draw3DRect(hero.shot.x, hero.shot.y, 1, 1, false);
        }
    }

    //编写方法，画出坦克
    /**
     * @param x      坦克的左上角x坐标
     * @param y      坦克的左上角y坐标
     * @param g      画笔
     * @param direct 坦克的方向
     * @param type   坦克类型 (用颜色标记我的坦克，还是敌人的坦克)
     */
    @SuppressWarnings("all")
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0:    //敌方坦克
                g.setColor(Color.cyan);
                break;
            case 1:    //我方坦克
                g.setColor(Color.yellow);
                break;

        }

        switch (direct) {
            case 0: //表示向上
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//画出炮筒
                break;
            case 1: //表示向右
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//画出炮筒
                break;
            case 2: //表示向下
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//画出炮筒
                break;
            case 3: //表示向左
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x, y + 20);//画出炮筒
                break;
            default:
                System.out.println("暂时不处理");

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理wsad按下的情况

    @Override
    @SuppressWarnings("all")
    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_W) {    //按下W键
            //改变坦克的方向
            hero.setDirect(0);
            //修改坦克的坐标
            hero.moveUp();

        } else if (e.getExtendedKeyCode() == KeyEvent.VK_D) {    //按下D键
            //改变坦克的方向
            hero.setDirect(1);
            hero.moveRight();
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_S) {    //按下S键
            //改变坦克的方向
            hero.setDirect(2);
            hero.moveDown();
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_A) {    //按下A键
            //改变坦克的方向
            hero.setDirect(3);
            hero.moveLeft();
        }

        //如果用户按下的时J, 就发射
        if (e.getExtendedKeyCode() == KeyEvent.VK_J) {
            System.out.println("用户按下了J键, 开始射击");
            hero.shotEnemyTank();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {     //每隔 100毫秒, 重绘区域, 刷新绘图区域, 子弹就移动
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
}
