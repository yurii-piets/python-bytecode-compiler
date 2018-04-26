package com.pbc.compiler;

import com.pbc.info.Constant;
import com.pbc.info.CreationTag;
import com.pbc.info.EntryTag;
import com.pbc.info.access.ClassAccessModifier;
import com.pbc.info.java.JavaVersion;
import com.pbc.util.HexUtil;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class ClassBuilder {

    private final String JavaMagicNumber = "cafebabe";

    private JavaVersion javaVersion;

    private List<Constant> constantPool = new ArrayList<>();

    private List<ClassAccessModifier> accessModifiers = new ArrayList<>();

    private String name;

    private String superName;

    private List<String> interfaces = new ArrayList<>();

    private List<String> fields = new ArrayList<>();

    private List<String> methods = new ArrayList<>();

    private List<String> attributes = new ArrayList<>();

    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append(JavaMagicNumber)
                .append(HexUtil.number(javaVersion.getMinor(), 4))
                .append(HexUtil.number(javaVersion.getMajor(), 4))
                .append(HexUtil.number(constantPool.size() > 0 ? constantPool.size() * 2 + 1 : 0, 4))
                .append(constantPool.stream().map(Constant::toCode).collect(Collectors.joining()))
                .append(HexUtil.classAccessModifiers(accessModifiers))

                .append("0001").append("0000")
                .append("0000")
                .append("0000")
                .append("0000")
                .append("0000");

        return builder.toString();
    }


    public ClassBuilder javaVersion(JavaVersion javaVersion) {
        this.javaVersion = javaVersion;
        return this;
    }

    public ClassBuilder constant(Constant constant) {
        constantPool.add(constant);
        return this;
    }

    public ClassBuilder accessModifiers(ClassAccessModifier... accessModifiers) {
        Collections.addAll(this.accessModifiers, accessModifiers);
        return this;
    }

    public ClassBuilder name(String name) {
        constant(new Constant(CreationTag.Class, EntryTag.UTF8, constantPool.size(), name));
        return this;
    }

    public ClassBuilder superName(String superName) {
        constant(new Constant(CreationTag.Class, EntryTag.UTF8, constantPool.size(), superName));
        return this;
    }

    public ClassBuilder intefaze(String intefaze) {
        interfaces.add(intefaze);
        return this;
    }

    public ClassBuilder field(String field) {
        fields.add(field);
        return this;
    }

    public ClassBuilder method(String method) {
        methods.add(method);
        return this;
    }

    public ClassBuilder attribute(String attribute) {
        attributes.add(attribute);
        return this;
    }

    public static ClassBuilder builder() {
        return new ClassBuilder();
    }
}
