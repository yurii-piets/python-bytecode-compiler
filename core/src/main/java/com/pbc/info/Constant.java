package com.pbc.info;

import com.pbc.util.HexUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Constant {

    private CreationTag creationTag;

    private EntryTag entryTag;

    private Integer poolIndex;

    private String value;

    public String toCode() {
        StringBuilder builder = new StringBuilder();

        builder.append(creationTag.getCode())
                .append(HexUtil.number((poolIndex + 1) * 2, 4))
                .append(entryTag.getCode())
                .append(HexUtil.number(value.length(), 4))
                .append(HexUtil.string(value));

        return builder.toString();
    }
}
