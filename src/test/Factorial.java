package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * ===
 * Created by slimon on 02.10.2015.
 */

public class Factorial {

    static File file = new File("out.txt");
    static FileWriter fw;

    public static void main(String[] args) throws InterruptedException, IOException {
        //Scanner s = new Scanner(System.in);
        int n = 1000000;//Integer.parseInt(args[0]);
        System.out.println("n = " + n);
        //Thread.sleep(2000);
        writeToFile(factorial(n)/* + System.lineSeparator() + parallelFactorial(n)*/);
        fw.close();
    }

    public static String factorial(int n) {
        BigInteger fact = new BigInteger("1");
        long lastTime = System.currentTimeMillis();
        long currTime;
        for (int i = 1; i <= n; i++) {
            if((currTime = System.currentTimeMillis()) - lastTime >= 1000) {
                System.out.println(i + "        " + i*100/n + "%");
                lastTime = currTime;
            }
            fact = fact.multiply(new BigInteger(i + ""));
        }
        System.out.println("Done!");
        return fact.toString();
    }

    public static String parallelFactorial(int n) {
        return "";
    }

    public static void writeToFile(String s) {
        if(!file.exists()) {
            try {
                if(!file.createNewFile()) return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(fw == null) {
            try {
                fw = new FileWriter(file, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fw.write(s + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}