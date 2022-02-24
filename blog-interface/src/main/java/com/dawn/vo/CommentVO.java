package com.dawn.vo;

import com.dawn.model.Comment;

import java.io.Serializable;
import java.util.List;

public class CommentVO implements Serializable {

    private Integer id;

    private Comment comment;

    private String parentNickname;

    private List<CommentVO> replyComments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<CommentVO> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<CommentVO> replyComments) {
        this.replyComments = replyComments;
    }

    public String getParentNickname() {
        return parentNickname;
    }

    public void setParentNickname(String parentNickname) {
        this.parentNickname = parentNickname;
    }
}
