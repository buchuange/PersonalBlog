package com.dawn.model;

/**
 * 博客和标签关联类
 */
public class BlogTagRelation {

    private Integer id;       // 主键 ID

    private Integer blogId;   // 博客ID

    private Integer tagId;    // 标签ID

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}