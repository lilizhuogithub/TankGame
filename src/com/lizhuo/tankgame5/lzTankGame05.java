package com.lizhuo.tankgame5;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 *画框
 */
public class lzTankGame05 extends JFrame {

    //定义MyPanel
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        lzTankGame05 lzTankGame01 = new lzTankGame05();

    }

    public lzTankGame05() {
        System.out.println("请输入选择 1: 新游戏 2: 继续上局");
        String key = scanner.next();
        mp = new MyPanel(key);
        //将mp放入到Thread, 并启动
        new Thread(mp).start();
        this.add(mp);    //把面板就是游戏绘制区域添加进来
        this.setSize(1300, 950);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(mp);    //让JFrame监听键盘事件
        this.setVisible(true);

        //在JFrame 中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });

    }
}
