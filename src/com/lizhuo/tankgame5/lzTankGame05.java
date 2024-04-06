package com.lizhuo.tankgame5;

import javax.swing.*;

/**
 *画框
 */
public class lzTankGame05 extends JFrame {

    //定义MyPanel
    MyPanel mp = null;

    public static void main(String[] args) {
        lzTankGame05 lzTankGame01 = new lzTankGame05();

    }

    public lzTankGame05() {
        mp = new MyPanel();    //初始化为空，构造器中完成初始化
        //将mp放入到Thread, 并启动
        new Thread(mp).start();
        this.add(mp);    //把面板就是游戏绘制区域添加进来
        this.setSize(1200, 950);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(mp);    //让JFrame监听键盘事件
        this.setVisible(true);
    }
}