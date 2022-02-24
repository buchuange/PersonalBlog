package com.dawn.service;

import com.dawn.model.Blog;
import com.dawn.vo.ArchivesVO;
import com.dawn.vo.IndexVO;
import com.dawn.vo.PaginationVO;

import java.util.List;


public interface BlogService {

    Blog getBlogById(Integer id);

    PaginationVO<Blog> listBlog(Integer pageNum, Integer pageSize, Blog blog);

     boolean saveBlog(Blog blog, Integer[] tagIds);

     boolean updateBlog(Blog blog, Integer[] tagIds);

     boolean deleteBlog(Integer id);

    PaginationVO<IndexVO> getBlogList(Integer pageNum, int pageSize);

    List<Blog> getRecommendBlogs(Integer size);

    PaginationVO<IndexVO> getSearchBlogs(Integer pageNum, int pageSize, String query);

    Blog increaseViews(Integer id);

    PaginationVO<IndexVO> listTypeBlogs(Integer pageNum, int pageSize, Integer id);

    PaginationVO<IndexVO> listTagBlogs(Integer pageNum, int pageSize, Integer id);


    List<ArchivesVO> listArchivesBlogs();

    int countBlogs();
}
