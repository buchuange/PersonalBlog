package com.dawn.model;

import java.io.Serializable;

/**
 * 博客类型类
 */
public class Type implements Serializable {

    private Integer id;    // 分类Id
    private String name;   // 分类名称

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