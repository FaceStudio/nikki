package com.inspur.nikki;

import java.security.MessageDigest;

public class MD5Utils {

    private static String byteArrayToHex(byte[] paramArrayOfByte) {
        int i = 0;
        char[] arrayOfChar1 = new char[16];
        char[] tmp10_8 = arrayOfChar1;
        tmp10_8[0] = 48;
        char[] tmp15_10 = tmp10_8;
        tmp15_10[1] = 49;
        char[] tmp20_15 = tmp15_10;
        tmp20_15[2] = 50;
        char[] tmp25_20 = tmp20_15;
        tmp25_20[3] = 51;
        char[] tmp30_25 = tmp25_20;
        tmp30_25[4] = 52;
        char[] tmp35_30 = tmp30_25;
        tmp35_30[5] = 53;
        char[] tmp40_35 = tmp35_30;
        tmp40_35[6] = 54;
        char[] tmp46_40 = tmp40_35;
        tmp46_40[7] = 55;
        char[] tmp52_46 = tmp46_40;
        tmp52_46[8] = 56;
        char[] tmp58_52 = tmp52_46;
        tmp58_52[9] = 57;
        char[] tmp64_58 = tmp58_52;
        tmp64_58[10] = 97;
        char[] tmp70_64 = tmp64_58;
        tmp70_64[11] = 98;
        char[] tmp76_70 = tmp70_64;
        tmp76_70[12] = 99;
        char[] tmp82_76 = tmp76_70;
        tmp82_76[13] = 100;
        char[] tmp88_82 = tmp82_76;
        tmp88_82[14] = 101;
        char[] tmp94_88 = tmp88_82;
        tmp94_88[15] = 102;
//        tmp94_88;
        char[] arrayOfChar2 = new char[paramArrayOfByte.length * 2];
        int k = paramArrayOfByte.length;
        int j = 0;
        while (i < k) {
            int l = paramArrayOfByte[i];
            int i1 = j + 1;
            arrayOfChar2[j] = arrayOfChar1[(l >>> 4 & 0xF)];
            j = i1 + 1;
            arrayOfChar2[i1] = arrayOfChar1[(l & 0xF)];
            i += 1;
        }
        return new String(arrayOfChar2);
    }

    public static String md5(String paramString) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            paramString = byteArrayToHex(localMessageDigest.digest());
            return paramString;
        } catch (Exception param) {

        }
        return null;
    }
}