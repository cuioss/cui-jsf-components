package de.cuioss.jsf.api.components.model.datalist.impl;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("javadoc")
@Data
@NoArgsConstructor
public class SomeModel implements Serializable {

    private static final long serialVersionUID = 5670597921615648898L;
    private String name;
    private Integer age;

    public SomeModel(final SomeModel other) {
        this.name = other.getName();
        this.age = other.getAge();
    }

    public SomeModel(final String name, final Integer age) {
        this.name = name;
        this.age = age;
    }
}
