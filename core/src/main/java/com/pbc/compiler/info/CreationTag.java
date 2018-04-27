package com.pbc.compiler.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CreationTag {
    Class("07"), String_Constant("08"), Field_Ref("09"), Methodref("0a");
    private String code;
}
