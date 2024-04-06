package com.lizhuo.tankgame5;

import java.io.BufferedWriter;
import java.io.FileWriter;

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


}
