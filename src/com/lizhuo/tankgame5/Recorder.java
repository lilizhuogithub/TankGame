package com.lizhuo.tankgame5;

import java.io.*;
import java.util.Vector;

/**
 *该类用于记录相关信息和文件交互
 */
public class Recorder {
    //定义变量， 巨鹿我方机会敌人坦克数
    private static int allEnemyTankNum = 0;
    //定义IO对象，准备写数据到文件中
    private static FileWriter fw = null;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "src\\myRocord.txt";
    //定义Vector ,指向 MyPanel 对象的 敌人坦克Vector
    private static Vector<EnemyTank> enemyTanks = null;
    //定义一个Node 的Vector ,用于保存敌人的信息node
    private static Vector<Node> nodes = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

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
    //对keepRecord 进行升级, 保存敌人坦克的坐标和方向
    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");
            //遍历敌人坦克的Vector ,然后根据情况保存即可.
            //OOP, 定义一个属性 ，然后通过setXxx得到 敌人坦克的Vector
            //遍历敌人坦克的Vector ,然后根据情况保存即可.
            //OOP, 定义一个属性 ，然后通过setXxx得到 敌人坦克的Vector
            for (int i = 0; i < enemyTanks.size(); i++) {
                //取出敌人坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) { //建议判断.
                    //保存该enemyTank信息
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    //写入到文件
                    bw.write(record + "\r\n");
                }
            }
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

    //增加一个方法，用于读取recordFile, 恢复相关信息
    //该方法，在继续上局的时候调用即可
    public static Vector<Node> getNodesAndEnemyTankRec() {

        try {

            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //循环读取文件，生成nodes 集合
            String line = "";//255 40 0
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]),
                        Integer.parseInt(xyd[2]));
                nodes.add(node);//放入nodes Vector
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return nodes;
    }

    public static String getRecordFile() {
        return recordFile;
    }
}
