package com.lizhuo.tankgame1;

import javax.swing.*;

/**
 *画框
 */
public class lzTankGame01 extends JFrame {

    //定义MyPanel
    MyPanel mp = null;

    public static void main(String[] args) {
        lzTankGame01 lzTankGame01 = new lzTankGame01();

    }

    public lzTankGame01() {
        mp = new MyPanel();    //初始化为空，构造器中完成初始化
        this.add(mp);    //把面板就是游戏绘制区域添加进来
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
