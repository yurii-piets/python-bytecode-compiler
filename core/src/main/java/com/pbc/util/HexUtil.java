package com.pbc.util;

import com.pbc.compiler.info.access.ClassAccessModifier;
import com.pbc.compiler.info.access.MethodAccessModifier;

import javax.xml.bind.DatatypeConverter;
import java.util.Collection;

public class HexUtil {

    public static String string(String string) {
        StringBuilder hexs = new StringBuilder();
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            hexs.append(String.format("%02x", (int) chars[i]));
        }
        return hexs.toString();
    }

    public static String number(Integer integer) {
        return Integer.toHexString(integer);
    }

    public static String number(Integer integer, Integer minNumberOfSymbols) {
        StringBuilder s = new StringBuilder(Integer.toHexString(integer));
        while (s.length() < minNumberOfSymbols) {
            s.insert(0, "0");
        }
        return s.toString();
    }

    public static String classAccessModifiers(Collection<ClassAccessModifier> classAccessModifiers) {
        Integer sum = 0;
        for (ClassAccessModifier classAccessModifier : classAccessModifiers) {
            sum += classAccessModifier.getCode();
        }
        return number(sum, 4);
    }

    public static String methodAcceessModifers(Collection<MethodAccessModifier> methodAccessModifiers) {
        Integer sum = 0;
        for (MethodAccessModifier methodAccessModifier : methodAccessModifiers) {
            sum += methodAccessModifier.getCode();
        }
        return Integer.toHexString(sum);
    }

    public static byte[] hexStringToByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}
