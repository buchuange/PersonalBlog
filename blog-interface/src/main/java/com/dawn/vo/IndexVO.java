package com.dawn.vo;

import com.dawn.model.Blog;
import com.dawn.model.Type;
import com.dawn.model.User;

import java.io.Serializable;

public class IndexVO implements Serializable {

    private Blog blog;
    private Type type;
    private User user;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
