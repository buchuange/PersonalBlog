package com.dawn.mapper;

import com.dawn.model.Comment;
import com.dawn.vo.CommentVO;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<CommentVO> listTopComment(Integer blogId);

    List<CommentVO> listTreeComment(Integer id);
}