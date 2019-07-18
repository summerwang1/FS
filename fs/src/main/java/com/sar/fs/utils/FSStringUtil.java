package com.sar.fs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auth: sarWang
 * @date: 2019-07-05 17:38
 * @describe
 */
public final class FSStringUtil {
    public FSStringUtil() {
    }

    public static String parseEmpty(String str) {
        if (str == null || "null".equals(str.trim())) {
            str = "";
        }

        return str.trim();
    }

    public static int chineseLength(String str) {
        int valueLength = 0;
        String chinese = "[Α-￥]";
        if (!isEmpty(str)) {
            for(int i = 0; i < str.length(); ++i) {
                String temp = str.substring(i, i + 1);
                if (temp.matches(chinese)) {
                    valueLength += 2;
                }
            }
        }

        return valueLength;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static int strLength(String str) {
        int valueLength = 0;
        String chinese = "[Α-￥]";
        if (!isEmpty(str)) {
            for(int i = 0; i < str.length(); ++i) {
                String temp = str.substring(i, i + 1);
                if (temp.matches(chinese)) {
                    valueLength += 2;
                } else {
                    ++valueLength;
                }
            }
        }

        return valueLength;
    }

    public static int subStringLength(String str, int maxL) {
        int currentIndex = 0;
        int valueLength = 0;
        String chinese = "[Α-￥]";

        for(int i = 0; i < str.length(); ++i) {
            String temp = str.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                ++valueLength;
            }

            if (valueLength >= maxL) {
                currentIndex = i;
                break;
            }
        }

        return currentIndex;
    }

    public static Boolean isMobileNo(String str) {
        Boolean isMobileNo = false;

        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(str);
            isMobileNo = m.matches();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return isMobileNo;
    }

    public static Boolean isNumberLetter(String str) {
        Boolean isNoLetter = false;
        String expr = "^[A-Za-z0-9]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }

        return isNoLetter;
    }

    public static Boolean isNumber(String str) {
        Boolean isNumber = false;
        String expr = "^[0-9]+$";
        if (str.matches(expr)) {
            isNumber = true;
        }

        return isNumber;
    }

    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (str.matches(expr)) {
            isEmail = true;
        }

        return isEmail;
    }

    public static Boolean isChinese(String str) {
        Boolean isChinese = true;
        String chinese = "[Α-￥]";
        if (!isEmpty(str)) {
            for(int i = 0; i < str.length(); ++i) {
                String temp = str.substring(i, i + 1);
                if (!temp.matches(chinese)) {
                    isChinese = false;
                }
            }
        }

        return isChinese;
    }

    public static Boolean isContainChinese(String str) {
        Boolean isChinese = false;
        String chinese = "[Α-￥]";
        if (!isEmpty(str)) {
            for(int i = 0; i < str.length(); ++i) {
                String temp = str.substring(i, i + 1);
                if (temp.matches(chinese)) {
                    isChinese = true;
                }
            }
        }

        return isChinese;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            if (sb.indexOf("\n") != -1 && sb.lastIndexOf("\n") == sb.length() - 1) {
                sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n") + 1);
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException var12) {
                var12.printStackTrace();
            }

        }

        return sb.toString();
    }

    public static String dateTimeFormat(String dateTime) {
        StringBuilder sb = new StringBuilder();

        try {
            if (isEmpty(dateTime)) {
                return null;
            }

            String[] dateAndTime = dateTime.split(" ");
            if (dateAndTime.length > 0) {
                String[] var3 = dateAndTime;
                int var4 = dateAndTime.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    String str = var3[var5];
                    String[] date;
                    int i;
                    String str1;
                    if (str.indexOf("-") != -1) {
                        date = str.split("-");

                        for(i = 0; i < date.length; ++i) {
                            str1 = date[i];
                            sb.append(strFormat2(str1));
                            if (i < date.length - 1) {
                                sb.append("-");
                            }
                        }
                    } else if (str.indexOf(":") != -1) {
                        sb.append(" ");
                        date = str.split(":");

                        for(i = 0; i < date.length; ++i) {
                            str1 = date[i];
                            sb.append(strFormat2(str1));
                            if (i < date.length - 1) {
                                sb.append(":");
                            }
                        }
                    }
                }
            }
        } catch (Exception var10) {
            var10.printStackTrace();
            return null;
        }

        return sb.toString();
    }

    public static String strFormat2(String str) {
        try {
            if (str.length() <= 1) {
                str = "0" + str;
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return str;
    }

    public static String cutString(String str, int length) {
        return cutString(str, length, "");
    }

    public static String cutString(String str, int length, String dot) {
        int strBLen = strlen(str, "GBK");
        if (strBLen <= length) {
            return str;
        } else {
            int temp = 0;
            StringBuffer sb = new StringBuffer(length);
            char[] ch = str.toCharArray();
            char[] var7 = ch;
            int var8 = ch.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                char c = var7[var9];
                sb.append(c);
                if (c > 256) {
                    temp += 2;
                } else {
                    ++temp;
                }

                if (temp >= length) {
                    if (dot != null) {
                        sb.append(dot);
                    }
                    break;
                }
            }

            return sb.toString();
        }
    }

    public static int strlen(String str, String charset) {
        if (str != null && str.length() != 0) {
            int length = 0;

            try {
                length = str.getBytes(charset).length;
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return length;
        } else {
            return 0;
        }
    }

    public static String cutStringFromChar(String str1, String str2, int offset) {
        if (isEmpty(str1)) {
            return "";
        } else {
            int start = str1.indexOf(str2);
            return start != -1 && str1.length() > start + offset ? str1.substring(start + offset) : "";
        }
    }

    public static long ip2int(String ip) {
        ip = ip.replace(".", ",");
        String[] items = ip.split(",");
        return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16 | Long.valueOf(items[2]) << 8 | Long.valueOf(items[3]);
    }

    public static String[] split(String str, String splitsign) {
        if (str != null && splitsign != null) {
            int index;
            ArrayList al;
            for(al = new ArrayList(); (index = str.indexOf(splitsign)) != -1; str = str.substring(index + splitsign.length())) {
                al.add(str.substring(0, index));
            }

            al.add(str);
            return (String[])((String[])al.toArray(new String[0]));
        } else {
            return null;
        }
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\r");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }

        return dest;
    }

    public static String replace(String from, String to, String source) {
        if (source != null && from != null && to != null) {
            StringBuffer bf = new StringBuffer("");
            boolean var4 = true;

            int index;
            while((index = source.indexOf(from)) != -1) {
                bf.append(source.substring(0, index) + to);
                source = source.substring(index + from.length());
                source.indexOf(from);
            }

            bf.append(source);
            return bf.toString();
        } else {
            return null;
        }
    }

    public static String toString(String str) {
        return str != null && !"".equals(str.trim()) && !"null".equals(str.trim()) ? str : "";
    }

    public static int toInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception var2) {
            return 0;
        }
    }

    public static long toLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception var2) {
            return 0L;
        }
    }

    public static double toDouble(String str) {
        try {
            double f = Double.parseDouble(str);
            BigDecimal b = new BigDecimal(f);
            double f1 = b.setScale(2, 4).doubleValue();
            return f1;
        } catch (Exception var6) {
            return 0.0D;
        }
    }

    public static boolean toBool(String str) {
        try {
            return Boolean.parseBoolean(str);
        } catch (Exception var2) {
            return false;
        }
    }

    public static String toDBC(String str) {
        char[] c = str.toCharArray();

        for(int i = 0; i < c.length; ++i) {
            if (c[i] == 12288) {
                c[i] = ' ';
            } else if (c[i] > '\uff00' && c[i] < '｟') {
                c[i] -= 'ﻠ';
            }
        }

        return new String(c);
    }

    public static String trimPhone(String str) {
        String dest = "";
        if (str != null) {
            str = str.replaceAll("-", "");
            str = str.replaceAll("\\+", "");
            dest = str;
        }

        return dest;
    }

    public static String getFileNameFromUrl(String url) {
        String extName = "";
        int index = url.lastIndexOf(63);
        if (index > 1) {
            extName = url.substring(url.lastIndexOf(46) + 1, index);
        } else {
            extName = url.substring(url.lastIndexOf(46) + 1);
        }

        String filename = hashKeyForDisk(url) + "." + extName;
        return filename;
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException var3) {
            cacheKey = String.valueOf(key.hashCode());
        }

        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < bytes.length; ++i) {
            String hex = Integer.toHexString(255 & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }

            sb.append(hex);
        }

        return sb.toString();
    }
}
