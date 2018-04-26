package com.pbc.info.java;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JavaVersion {

    _1_0(45, 3),
    _1_1(45, 3),
    _1_2(46, 0),
    _1_3(47, 0),
    _1_4(48, 0),
    _5(49, 0),
    _6(50, 0),
    _7(51, 0),
    _8(52, 0),
    _9(53, 0),
    _10(54, 0);

    private int major;

    private int minor;
}
