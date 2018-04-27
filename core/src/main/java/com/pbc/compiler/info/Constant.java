package com.pbc.compiler.info;

import com.pbc.util.HexUtil;
import lombok.Getter;

@Getter
public class Constant {

    private CreationTag creationTag;

    private EntryTag entryTag;

    private Integer poolIndex;

    private static Integer poolSize = 1;

    private String value;

    public Constant(CreationTag creationTag, EntryTag entryTag, String value) {
        this.creationTag = creationTag;
        this.entryTag = entryTag;
        this.value = value;
        this.poolIndex = poolSize++;
    }

    public String toCode() {
        StringBuilder builder = new StringBuilder();

        builder.append(creationTag.getCode())
                .append(HexUtil.number(poolIndex * 2, 4))
                .append(entryTag.getCode())
                .append(HexUtil.number(value.length(), 4))
                .append(HexUtil.string(value));

        return builder.toString();
    }
}
