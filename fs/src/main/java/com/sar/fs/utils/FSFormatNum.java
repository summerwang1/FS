package com.sar.fs.utils;

import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @auth: sarWang
 * @date: 2019-07-05 17:42
 * @describe
 */
public class FSFormatNum {
    public static final String TAG = "FSFormatNum";

    public FSFormatNum() {
    }

    public static String formatNumber(double d, String pattern) {
        return formatNumber(d, pattern, Locale.getDefault());
    }

    public static String formatNumber(double d, String pattern, Locale l) {
        String s = "";

        try {
            DecimalFormat nf = (DecimalFormat) NumberFormat.getInstance(l);
            nf.applyPattern(pattern);
            s = nf.format(d);
        } catch (Exception var6) {
            Log.e("FSFormatNum", "formatNumber: ", var6);
        }

        return s;
    }

    public static String formatCurrency(double d) {
        String s = "";

        try {
            DecimalFormat nf = (DecimalFormat)NumberFormat.getCurrencyInstance();
            s = nf.format(d);
        } catch (Exception var4) {
            Log.e("FSFormatNum", "formatCurrency: ", var4);
        }

        return s;
    }

    public static String formatPercent(double d, String pattern) {
        return formatPercent(d, pattern, Locale.getDefault());
    }

    public static String formatPercent(double d, String pattern, Locale l) {
        String s = "";

        try {
            DecimalFormat df = (DecimalFormat)NumberFormat.getPercentInstance(l);
            df.applyPattern(pattern);
            s = df.format(d);
        } catch (Exception var6) {
            Log.e("FSFormatNum", "formatPercent: ", var6);
        }

        return s;
    }

    public static String formatPercent(double d) {
        String s = "";

        try {
            DecimalFormat df = (DecimalFormat)NumberFormat.getPercentInstance();
            s = df.format(d);
        } catch (Exception var4) {
            Log.e("FSFormatNum", "formatPercent: ", var4);
        }

        return s;
    }

    public static String numberFormat(BigDecimal bd, String format) {
        if (bd != null && !"0".equals(bd.toString())) {
            DecimalFormat bf = new DecimalFormat(format);
            return bf.format(bd);
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        String s = formatCurrency(11123.89343D, "$##,##.000");
        s = formatCurrency(1234567.89343D, ",###");
        System.out.println(s);
    }

    public static String formatCurrency(double d, String pattern) {
        return formatCurrency(d, pattern, Locale.getDefault());
    }

    public static String formatCurrency(double d, String pattern, Locale l) {
        String s = "";

        try {
            DecimalFormat nf = (DecimalFormat)NumberFormat.getCurrencyInstance(l);
            nf.applyPattern(pattern);
            s = nf.format(d);
        } catch (Exception var6) {
            Log.e("FSFormatNum", "formatCurrency: ", var6);
        }

        return s;
    }
}

