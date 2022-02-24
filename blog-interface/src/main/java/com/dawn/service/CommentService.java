package com.dawn.service;

import com.dawn.model.Comment;
import com.dawn.vo.CommentVO;

import java.util.List;

public interface CommentService {

    List<CommentVO> listCommentByBlogId(Integer blogId);

    boolean saveComment(Comment comment);
}
