package com.lizhuo.tankgame2;

import javax.swing.*;

/**
 *画框
 */
public class lzTankGame02 extends JFrame {

    //定义MyPanel
    MyPanel mp = null;

    public static void main(String[] args) {
        lzTankGame02 lzTankGame01 = new lzTankGame02();

    }

    public lzTankGame02() {
        mp = new MyPanel();    //初始化为空，构造器中完成初始化
        this.add(mp);    //把面板就是游戏绘制区域添加进来
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(mp);    //让JFrame监听键盘事件
        this.setVisible(true);
    }
}
