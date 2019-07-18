package com.sar.fs.utils;

import java.math.BigDecimal;

/**
 * @auth: sarWang
 * @date: 2019-07-05 17:46
 * @describe
 */
public class FSMathUtil {
    public FSMathUtil() {
    }

    public static BigDecimal round(double number, int decimal) {
        return (new BigDecimal(number)).setScale(decimal, 4);
    }

    public static String byte2HexStr(byte[] b, int length) {
        String hs = "";
        String stmp = "";

        for(int n = 0; n < length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }

            hs = hs + ",";
        }

        return hs.toUpperCase();
    }

    public static char binaryToHex(int binary) {
        char ch;
        switch(binary) {
            case 0:
                ch = '0';
                break;
            case 1:
                ch = '1';
                break;
            case 2:
                ch = '2';
                break;
            case 3:
                ch = '3';
                break;
            case 4:
                ch = '4';
                break;
            case 5:
                ch = '5';
                break;
            case 6:
                ch = '6';
                break;
            case 7:
                ch = '7';
                break;
            case 8:
                ch = '8';
                break;
            case 9:
                ch = '9';
                break;
            case 10:
                ch = 'a';
                break;
            case 11:
                ch = 'b';
                break;
            case 12:
                ch = 'c';
                break;
            case 13:
                ch = 'd';
                break;
            case 14:
                ch = 'e';
                break;
            case 15:
                ch = 'f';
                break;
            default:
                ch = ' ';
        }

        return ch;
    }

    public static int[][] arrayToMatrix(int[] m, int width, int height) {
        int[][] result = new int[height][width];

        for(int i = 0; i < height; ++i) {
            for(int j = 0; j < width; ++j) {
                int p = j * height + i;
                result[i][j] = m[p];
            }
        }

        return result;
    }

    public static double[] matrixToArray(double[][] m) {
        int p = m.length * m[0].length;
        double[] result = new double[p];

        for(int i = 0; i < m.length; ++i) {
            for(int j = 0; j < m[i].length; ++j) {
                int q = j * m.length + i;
                result[q] = m[i][j];
            }
        }

        return result;
    }

    public static double[] intToDoubleArray(int[] input) {
        int length = input.length;
        double[] output = new double[length];

        for(int i = 0; i < length; ++i) {
            output[i] = Double.valueOf(String.valueOf(input[i]));
        }

        return output;
    }

    public static double[][] intToDoubleMatrix(int[][] input) {
        int height = input.length;
        int width = input[0].length;
        double[][] output = new double[height][width];

        for(int i = 0; i < height; ++i) {
            for(int j = 0; j < width; ++j) {
                output[i][j] = Double.valueOf(String.valueOf(input[i][j]));
            }
        }

        return output;
    }

    public static int average(int[] pixels) {
        float m = 0.0F;

        for(int i = 0; i < pixels.length; ++i) {
            m += (float)pixels[i];
        }

        m /= (float)pixels.length;
        return (int)m;
    }

    public static int average(double[] pixels) {
        float m = 0.0F;

        for(int i = 0; i < pixels.length; ++i) {
            m = (float)((double)m + pixels[i]);
        }

        m /= (float)pixels.length;
        return (int)m;
    }

    public static double sum(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }

    public static double sub(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    public static double mul(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    public double div(double d1, double d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2, scale, 4).doubleValue();
    }
}
