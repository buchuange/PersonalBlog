package com.dawn.mapper;

import com.dawn.model.BlogTagRelation;

public interface BlogTagRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogTagRelation record);

    int insertSelective(BlogTagRelation record);

    BlogTagRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BlogTagRelation record);

    int updateByPrimaryKey(BlogTagRelation record);

    Integer[] getTagIdsByBlogId(Integer id);

    int deleteTagIdsByBlogId(Integer id);

    String[] getTagsByBlogId(Integer id);
}