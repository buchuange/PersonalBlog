package com.dawn.mapper;

import com.dawn.model.Blog;
import com.dawn.vo.ArchivesVO;
import com.dawn.vo.IndexVO;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);

    int getTotalByCondition(Map<String, Object> map);

    List<Blog> getBlogListByCondition(Map<String, Object> map);

    int getPublishedTotal(Map<String, Object> map);

    List<IndexVO> getPublishedBlogs(Map<String, Object> map);

    List<Blog> getRecommendBlogs(Integer size);

    int getSearchTotal(String query);

    List<IndexVO> getSearchBlogs(Map<String, Object> map);

    int getTotalByTagId(Map<String, Object> map);

    List<IndexVO> listBlogsByTagId(Map<String, Object> map);

    List<ArchivesVO> listArchivesBlogs();

    List<Blog> archivesBlogs(String year);
}