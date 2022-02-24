package com.dawn.vo;

import com.dawn.model.Blog;

import java.io.Serializable;
import java.util.List;

public class ArchivesVO implements Serializable {

    String year;

    List<Blog> blogs;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
