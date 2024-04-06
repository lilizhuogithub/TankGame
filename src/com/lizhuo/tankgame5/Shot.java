package com.lizhuo.tankgame5;

/**
 *射击的子弹
 */
public class Shot implements Runnable{
    int x;    //子弹x坐标
    int y;    //子弹y坐标
    int direct = 0;    //子弹方向
    int speed = 2;    //子弹速度
    boolean isLive = true;    //子弹是否存活

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {     //射击
        while (true) {
            //休眠50ms为了看到子弹的效果
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向改变x, y的坐标
            switch (direct) {
                case 0://上
                    y -= speed;
                    break;
                case 1://右
                    x += speed;
                    break;
                case 2://下
                    y += speed;
                    break;
                case 3://左
                    x -= speed;
                    break;
            }
            //测试,这里输出x, y 坐标
//            System.out.println("子弹 x=" + x + "y=" + y);
            //当子弹移动到面板的边界时, 就应该销毁(把启动的子弹的线程销毁)
            //当子弹碰到敌人坦克时，也应该结束线程
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)) {
                System.out.println("子弹线程退出");
                isLive = false;
                break;
            }
        }

    }
}
