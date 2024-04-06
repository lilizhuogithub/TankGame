package com.lizhuo.tankgame5;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *该类用于记录相关信息和文件交互
 */
public class Recorder {
    //定义变量， 巨鹿我方机会敌人坦克数
    private static int allEnemyTankNum = 0;
    //定义IO对象，准备写数据到文件中
    private static FileWriter fw = null;
    private static BufferedWriter bw = null;
    private static String recordFile = "D:\\myRocord.txt";

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //当我方坦克击毁一辆敌人坦克就数量++
    public static void addAllEnemyTankNum() {
       Recorder.allEnemyTankNum++;
    }

    //增加一个方法，当游戏退出时，我们将allEnemyTankNum 保存到 recordFile
    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");
            //遍历敌人坦克的Vector ,然后根据情况保存即可.
            //OOP, 定义一个属性 ，然后通过setXxx得到 敌人坦克的Vector
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
