package com.pbc.utils;

import javax.xml.bind.DatatypeConverter;

public class HexUtil {

    public static byte[] hexStringToByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}
