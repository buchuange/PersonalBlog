package com.dawn.model;

import java.io.Serializable;

/**
 * 标签类
 */
public class Tag implements Serializable {

    private Integer id;      // 标签Id
    private String name;     // 标签名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}