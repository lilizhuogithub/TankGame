package com.lizhuo.tankgame5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
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
    //说明: 当子弹击中坦克时, 加入一个Bomb对象到bombs
    Vector<Bomb> bombs = new Vector<>();    //定义一个Vector, 用于存放炸弹

    //定义三张炸弹图片， 用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    //定义一个存放Node 对象的Vector, 用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();

    public MyPanel(String key) {
        //先判断记录的文件是否存在
        //如果存在，就正常执行，如果文件不存在，提示，只能开启新游戏，key = "1"
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndEnemyTankRec();
        } else {
            System.out.println("文件不存在，只能开启新的游戏");
            key = "1";
        }
        //将MyPanel对象的 enemyTanks 设置给 Recorder 的 enemyTanks
        Recorder.setEnemyTanks(enemyTanks);

        hero = new Hero(500, 100);//初始化自己坦克

        switch (key) {
            case "1":
                //初始化敌人坦克
                for (int i = 0; i < enemyTanksSize; i++) {
                    //创建一个敌人的坦克
                    EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
                    //将enemyTanks 设置给 enemyTank !!!
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirect(2);
                    //启动敌人坦克线程，让他动起来
                    new Thread(enemyTank).start();
                    //给该enemyTank 加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入enemyTank的Vector 成员
                    enemyTank.shots.add(shot);
                    //启动 shot 对象
                    new Thread(shot).start();
                    //加入
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2": //继续上局游戏
                //初始化敌人坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //创建一个敌人的坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //将enemyTanks 设置给 enemyTank !!!
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirect(node.getDirect());
                    //启动敌人坦克线程，让他动起来
                    new Thread(enemyTank).start();
                    //给该enemyTank 加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入enemyTank的Vector 成员
                    enemyTank.shots.add(shot);
                    //启动 shot 对象
                    new Thread(shot).start();
                    //加入
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误...");
        }
        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));

    }

    //编写方法，显示我方击毁敌方坦克的信息
    public void showInfo(Graphics g) {

        //画出玩家的总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("您累积击毁敌方坦克", 1020, 30);
        drawTank(1020, 60, g, 0, 0);//画出一个敌方坦克
        g.setColor(Color.BLACK);//这里需要重新设置成黑色
        g.drawString( Recorder.getAllEnemyTankNum() + "", 1080, 100);

    }

    @Override
    @SuppressWarnings("all")
    public void paint(Graphics g) {
        super.paint(g);
        showInfo(g);
        g.fillRect(0, 0, 1000, 750);    //填充矩形，默认是黑色
        //画出坦克-封装方法
        if (hero.isLive && hero != null) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);    //画出我方坦克
        }
        for (EnemyTank enemyTank : enemyTanks) {    //画出敌人坦克
            //判断当前坦克是否还存活
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //画出 enemyTank 所有的子弹
                for (int i = 0; i < enemyTank.shots.size(); i++) {
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
        }

        //画出hero射击的子弹
        if (hero.shot != null && hero.shot.isLive == true) {
            g.draw3DRect(hero.shot.x, hero.shot.y, 1, 1, false);
        }

        //如果bombs中 有对象, 就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life值去画出对应的图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            }
            //让炸弹的生命值减少
            bomb.lifeDown();
            //如果炸弹的生命值为0, 就从bombs的集合中删除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }


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

    public void hitEnemyTank() {
        //判断是否集中了敌人坦克
        if (hero.shot != null && hero.shot.isLive) {    //当我的子弹还存活
            //遍历敌人所有的坦克
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                hitTank(hero.shot, enemyTank);
            }
        }
    }

    //判断敌人坦克是否击中我的坦克
    public void hitHero() {
        //遍历所有的敌人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历enemyTank对象的所有子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                //取出子弹
                Shot shot = enemyTank.shots.get(j);
                //判断shot是否击中我的坦克
                if (hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }
            }
        }
    }

    //编写方法，判断子弹是否击中坦克
    //最好在run方法中判断
    public void hitTank(Shot s, Tank tank) {
        //判断子弹集中坦克
        switch (tank.getDirect()) {
            case 0:    //坦克向上
            case 2:    //坦克向下
                if (s.x > tank.getX() && s.x < tank.getX() + 40 &&
                        s.y > tank.getY() && s.y < tank.getY() + 60) {
                    s.isLive = false;
                    tank.isLive = false;
                    //当我的子弹击中敌人坦克后, 将enemyTank 从Vector中拿掉
                    enemyTanks.remove(tank);
                    //当我方子弹击中敌人坦克后，就执行allEnemyTankNum++
                    if (tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    //创建Bomb对象, 加入到bombs中
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:    //坦克向右
            case 3:    //坦克向左
                if (s.x > tank.getX() && s.x < tank.getX() + 60 &&
                        s.y > tank.getY() && s.y < tank.getY() + 40) {
                    s.isLive = false;
                    tank.isLive = false;
                    //当我的子弹击中敌人坦克后, 将enemyTank 从Vector中拿掉
                    enemyTanks.remove(tank);
                    //当我方子弹击中敌人坦克后，就执行allEnemyTankNum++
                    if (tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    //创建Bomb对象, 加入到bombs中
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
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
            if (hero.getY() > 0) {
                hero.moveUp();
            }

        } else if (e.getExtendedKeyCode() == KeyEvent.VK_D) {    //按下D键
            //改变坦克的方向
            hero.setDirect(1);
            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_S) {    //按下S键
            //改变坦克的方向
            hero.setDirect(2);
            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_A) {    //按下A键
            //改变坦克的方向
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        }

        //如果用户按下的时J, 就发射
        if (e.getExtendedKeyCode() == KeyEvent.VK_J) {
            //判断hero的子弹是否销毁 第一次为空才触发子弹, 第二次使用判断子弹打到边界没有存活才触发
            if (hero.shot == null || !hero.shot.isLive) {
                hero.shotEnemyTank();
            }
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

            //判断是我们子弹否击中了敌人坦克
            hitEnemyTank();
            //判断是敌人子弹否击中了我们的坦克
            hitHero();
            this.repaint();
        }
    }
}
