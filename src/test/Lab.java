package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * ===
 * Created by slimon on 12.10.2015.
 */
public class Lab {

    static File file = new File("C:\\Users\\user\\Desktop\\out.txt");
    static FileWriter fw;

    public static void main(String[] args) throws IOException {
        lab8();
        //lab9();
        //lab10a();
        //System.out.println(Math.sqrt(Math.pow(0.26, 2) + Math.pow(0.29, 2))/2);

        fw.close();
    }

    public static void lab6() {
        double[] x0 = new double[] {
                15.03,
                15.04,
                15.04,
                15.04,
                15.04
        };
        double[] x12 = new double[] {
                18.37,
                18.36,
                18.36,
                18.36,
                18.36
        };


    }

    public static void lab10a() {
        double[] U1_5sec = new double[] {
                0.050,
                0.070,
                0.171,
                0.288,
                0.394,
                0.492,
                0.603,
                0.699,
                0.792,
                0.881,
                0.973,
                1.073,
                1.167,
                1.269,
                1.367,
                1.457,
                1.542,
                1.637,
                1.717,
                1.806,
                1.879,
                1.963,
                2.043,
                2.128,
                2.201,
                2.262,
                2.348,
                2.396,
                2.459,
                2.520,
                2.581,
                2.651,
                2.692,
                2.741,
                2.784,
                2.835,
                2.883,
                2.936,
                2.988,
                3.018,
                3.046,
                3.088,
                3.120,
                3.144,
                3.149,
                3.169,
                3.178,
                3.187,
                3.195
        };

        int firstOneMinute1 = 300;

        double[] U2_60sec = new double[] {
                3.201,
                3.175,
                3.176,
                3.109,
                3.160,
                3.135,
                3.061,
                3.040,
                2.917,
                3.014,
                3.046,
                3.025,
                3.024,
                3.028,
                3.001,
                3.014,
                2.952
        };

        int firstAfterBoiling1 = 1317;

        double[] U3_10sec = new double[] {
                2.270,
                2.267,
                2.201,
                2.182,
                2.281,
                2.768,
                2.918,
                2.934,
                2.950,
                3.882,
                3.825,
                5.796
        };

        double[] U4_5sec = new double[] {
                0.050,
                0.124,
                0.098,
                0.076,
                0.137,
                0.330,
                0.444,
                0.611,
                0.733,
                0.871,
                0.993,
                1.120,
                1.227,
                1.342,
                1.459,
                1.566,
                1.671,
                1.760,
                1.870,
                1.951,
                2.047,
                2.140,
                2.205,
                2.286,
                2.364,
                2.428,
                2.498,
                2.558,
                2.614,
                2.652,
                2.694,
                2.716,
                2.751,
                2.751,
                2.769,
                2.800,
                2.799,
                2.833,
                2.858,
                2.869
        };

        int firstOneMinute2 = 255;

        double[] U5_60sec = new double[] {
                2.951,
                3.007,
                2.977,
                2.981,
                2.964,
                2.958,
                3.011,
                2.774
        };

        int firstAfterBoiling2 = 720;

        double[] U6_10sec = new double[] {
                2.240,
                2.246,
                2.186,
                2.127,
                2.069,
                2.220,
                2.607,
                2.780,
                2.854,
                2.831,
                2.776,
                3.500,
                5.685
        };

        int T = 292;
        double k = 24.84;
    }

    public static void lab9() {
        double[] U = new double[] {
                0.48,
                0.519,
                0.534,
                0.545,
                0.550,
                0.557,
                0.561,
                0.565,
                0.568,
                0.57,
                0.574,
                0.575,
                0.578,
                0.58,
                0.582,
                0.584,
                0.585,
                0.587,
                0.588,
                0.59
        };

        double[] lnU = new double[] {
                -5.9145,
                -4.3662,
                -3.8122,
                -3.3903,
                -3.1725,
                -2.9077,
                -2.7504,
                -2.5983,
                -2.4937,
                -2.3958,
                -2.2643,
                -2.1973,
                -2.1046,
                -2.0280,
                -1.9512,
                -1.8806,
                -1.8195,
                -1.7556,
                -1.7093,
                -1.6451
        };

        double[] k = new double[10];

        for(int i = 0; i < 10; i++) {
            k[i] = (lnU[i+10] - lnU[i]) / (U[i+10] - U[i]);
        }

        double averageK = average(k);
        double q = accuracy(k);
        double dk = averageK * q * 1.1;
        double b = 1.6*Math.pow(10, -19)/38.48/296;
        double db = Math.sqrt(Math.pow(0.27/38.48,2)+Math.pow(0.2/296.,2));

        writeToFile("averageK = " + averageK);
        writeToFile("q = " + q);
        writeToFile("dk = " + dk);
        writeToFile("b = " + b);
        writeToFile("db = " + db);
    }

    public static void lab8() throws IOException {

        //fw = new FileWriter(file);

        double m = 10.1 * 0.001;
        double D = 15 * 0.001;
        double V0 = 119 * 0.000001;
        double p0 = 102900;

        double[] T01 = new double[] {
                176.8,
                177.2,
                176.0,
                176.4,
                180.2,
                177.3,
                183.4,
                186.4,
                175.2
        };

        double[] T02 = new double[] {
                83.6,
                83.3,
                82.9,
                82.7,
                83.3,
                83.2,
                83.0,
                82.4,
                83.3
        };

        double[] A = new double[] {
                38,
                40,
                40,
                40,
                41,
                41,
                40,
                40,
                40
        };

        double[] T = new double[] {
                78.3,
                79.3,
                80.3,
                81.3,
                82.3,
                83.3,
                84.3,
                85.3,
                86.3,
                87.3,
                88.3
        };

        double[] Am = new double[] {
                11,
                15,
                21,
                29,
                38,
                40,
                35,
                30,
                26,
                23,
                21
        };

        double[] f01 = new double[T01.length];
        double[] f02 = new double[T02.length];
        double[] f = new double[T.length];

        for(int i = 0; i < T01.length; i++) {
            f01[i] = 1000 / T01[i];
        }
        for(int i = 0; i < T02.length; i++) {
            f02[i] = 1000 / T02[i];
        }
        for(int i = 0; i < T.length; i++) {
            f[i] = 1000 / T[i];
        }

        double averageT01 = average(T01);
        double averageT02 = average(T02);
        double averageA = average(A);
        double averageF01 = average(f01);
        double averageF02 = average(f02);

        double[] f01mAv = fMinusAverageF(f01);
        double[] f02mAv = fMinusAverageF(f02);

        double[] sqF01mAv = sqFMinusAverageF(f01);
        double[] sqF02mAv = sqFMinusAverageF(f02);

        //averageF01 = 7.05;

        double f0 = 1000 / 83.3;
        double AMax = 40;

        double y = 64 * m * V0 / p0 / Math.pow(D, 4) * (Math.pow(averageF02, 2) - Math.pow(averageF01, 2));

        writeToFile("Table 1");
        writeToFile("m=" + m);
        writeToFile("D=" + D);
        writeToFile("V0=" + V0);
        writeToFile("p0=" + p0);
        for(int n = 0; n < T01.length; n++) {
            writeToFile("N=" + (n+1) + "  T01=" + T01[n] + "  f01=" + f01[n] + "  f01-<f01>=" + f01mAv[n] +
                    "  T02=" + T02[n] + "  f02=" + f02[n] + "  f02-<f02>=" + f02mAv[n] +
                    "  (f01-<f01>)^2=" + sqF01mAv[n] + "  (f02-<f02>)^2=" + sqF02mAv[n]);
        }

        writeToFile(System.lineSeparator());

        writeToFile("Table 2");
        writeToFile("f0=" + f0);
        writeToFile("Um=" + AMax);
        for(int n = 0; n < T.length; n++) {
            writeToFile("N=" + (n+1) + "  T=" + T[n] + "  f=" + f[n] +
                    "  U=" + Am[n] + "  U/Um=" + Am[n]/AMax);
        }

        writeToFile(System.lineSeparator());
        writeToFile("average T01 = " + averageT01);
        writeToFile("average T02 = " + averageT02);
        writeToFile("average f01 = " + averageF01);
        writeToFile("average f02 = " + averageF02);
        writeToFile("average A = " + averageA);
        writeToFile("y = " + y);

        double dy, dm, dV0, dP0, dD, Df1, Df2, df;
        dm = 0.1 / 10.1;
        dV0 = 1. / 119;
        dP0 = 100. / 102900;
        dD = 0.1 / 15;
        Df1 = accuracy(f01);
        Df2 = accuracy(f02);
        df = Math.sqrt(Math.pow(2 * averageF02 * Df2, 2) + Math.pow(2 * averageF01 * Df1, 2)) /
                (Math.pow(averageF02, 2) - Math.pow(averageF01, 2));
        dy = Math.sqrt(dm*dm + dV0*dV0 + dP0*dP0 + 16*dD*dD + df*df);

        writeToFile("dm = " + dm);
        writeToFile("dV0 = " + dV0);
        writeToFile("dP0 = " + dP0);
        writeToFile("dD = " + dD);
        writeToFile("Df1 = " + Df1);
        writeToFile("Df2 = " + Df2);
        writeToFile("df = " + df);
        writeToFile("dy = " + dy);
        writeToFile("Dy = " + y*dy);
    }

    private static double[] sumOfArrays(double[] source1, double[] source2) {
        double[] sum = new double[source1.length + source2.length];
        System.arraycopy(source1, 0, sum, 0, source1.length);
        System.arraycopy(source2, 0, sum, source1.length, source2.length);
        return sum;
    }

    private static double accuracy(double[] source) {
        return Math.sqrt(variance(source) / source.length);
    }

    private static double[] sqFMinusAverageF(double[] source) {
        double average = average(source);
        double[] ret = new double[source.length];
        for(int i = 0; i < source.length; i++) {
            ret[i] = Math.pow(source[i] - average, 2);
        }
        return ret;
    }

    private static double standardDeviation(double... source) {
        return Math.sqrt(variance(source));
    }

    private static double[] fMinusAverageF(double[] source) {
        double average = average(source);
        double[] ret = new double[source.length];
        for(int i = 0; i < source.length; i++) {
            ret[i] = source[i] - average;
        }
        return ret;
    }

    private static double variance(double... source) {
        double average = average(source);
        double variance = 0;
        for(double d : source) {
            variance += Math.pow(d - average, 2);
        }
        variance /= (source.length - 1);
        return variance;
    }

    private static double average(double... source) {
        return sum(source) / source.length;
    }

    private static double sqSum(double... source) {
        double sum = 0;
        for(double d : source) {
            sum += d*d;
        }
        return sum;
    }

    private static double sum(double... source) {
        double sum = 0;
        for(double d : source) {
            sum += d;
        }
        return sum;
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
                fw = new FileWriter(file);
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
