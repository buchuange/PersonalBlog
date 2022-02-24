package com.dawn.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dawn.exception.NotFoundException;
import com.dawn.mapper.BlogMapper;
import com.dawn.mapper.BlogTagRelationMapper;
import com.dawn.mapper.TypeMapper;
import com.dawn.model.Blog;
import com.dawn.model.BlogTagRelation;
import com.dawn.vo.ArchivesVO;
import com.dawn.vo.IndexVO;
import com.dawn.vo.PaginationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service(interfaceClass = BlogService.class, version = "1.0.0", timeout = 15000)
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private BlogTagRelationMapper relationMapper;

    @Transactional
    @Override
    public Blog getBlogById(Integer id) throws NotFoundException{

        Blog blog = blogMapper.selectByPrimaryKey(id);

        if (blog == null) {
            throw new NotFoundException("该篇博客不存在，请前往首页查看已有的博客！");
        }

        return blog;
    }

    @Transactional
    @Override
    public PaginationVO<Blog> listBlog(Integer pageNum, Integer pageSize, Blog blog) {

        pageNum = (pageNum - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        map.put("title", blog.getTitle());
        map.put("typeId", blog.getTypeId());
        map.put("recommend", blog.getRecommend());

        int total = blogMapper.getTotalByCondition(map);
        List<Blog> dataList = blogMapper.getBlogListByCondition(map);

        PaginationVO<Blog> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Transactional
    @Override
    public boolean saveBlog(Blog blog, Integer[] tagIds) {

        boolean flag = true;

        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        int count = blogMapper.insertSelective(blog);

        if (count != 1) {
            flag = false;
        }

        for (int tagId : tagIds) {

            BlogTagRelation relation = new BlogTagRelation();
            relation.setBlogId(blog.getId());
            relation.setTagId(tagId);
            int count2 = relationMapper.insertSelective(relation);
            if (count2 != 1) {
                flag = false;
            }
        }

        return flag;
    }

    @Transactional
    @Override
    public boolean deleteBlog(Integer id) {

        boolean flag = true;

        Integer[] tags = relationMapper.getTagIdsByBlogId(id);
        int count = relationMapper.deleteTagIdsByBlogId(id);

        if (tags.length != count) {
            flag = false;
        }


        int count2 = blogMapper.deleteByPrimaryKey(id);

        if (count2 != 1) {
            flag = false;
        }

        return flag;
    }

    @Transactional
    @Override
    public boolean updateBlog(Blog blog, Integer[] tagIds) {

        boolean flag = true;

        Integer[] tags = relationMapper.getTagIdsByBlogId(blog.getId());
        int count = relationMapper.deleteTagIdsByBlogId(blog.getId());

        if (tags.length != count) {
            flag = false;
        }

        blog.setUpdateTime(new Date());
        int count2 = blogMapper.updateByPrimaryKeySelective(blog);
        if (count2 != 1) {
            flag = false;
        }

        for (int tagId : tagIds) {

            BlogTagRelation relation = new BlogTagRelation();
            relation.setBlogId(blog.getId());
            relation.setTagId(tagId);
            int count3 = relationMapper.insertSelective(relation);
            if (count3 != 1) {
                flag = false;
            }
        }

        return flag;
    }

    @Transactional
    @Override
    public PaginationVO<IndexVO> getBlogList(Integer pageNum, int pageSize) {

        pageNum = (pageNum - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);

        int total = blogMapper.getPublishedTotal(map);
        List<IndexVO> dataList = blogMapper.getPublishedBlogs(map);

        PaginationVO<IndexVO> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Override
    public List<Blog> getRecommendBlogs(Integer size) {
        List<Blog> blogs = blogMapper.getRecommendBlogs(size);
        return blogs;
    }

    @Override
    public PaginationVO<IndexVO> getSearchBlogs(Integer pageNum, int pageSize, String query) {
        pageNum = (pageNum - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        map.put("query", query);

        int total = blogMapper.getSearchTotal(query);
        List<IndexVO> dataList = blogMapper.getSearchBlogs(map);

        PaginationVO<IndexVO> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Transactional
    @Override
    public Blog increaseViews(Integer id) {

        Blog blog = blogMapper.selectByPrimaryKey(id);
        int views = blog.getViews();
        blog.setViews(views + 1);

        blogMapper.updateByPrimaryKeySelective(blog);

        return blog;
    }

    @Override
    public PaginationVO<IndexVO> listTypeBlogs(Integer pageNum, int pageSize, Integer id) {

        pageNum = (pageNum - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        map.put("typeId", id);

        int total = blogMapper.getPublishedTotal(map);
        List<IndexVO> dataList = blogMapper.getPublishedBlogs(map);

        PaginationVO<IndexVO> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Override
    public PaginationVO<IndexVO> listTagBlogs(Integer pageNum, int pageSize, Integer id) {

        pageNum = (pageNum - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        map.put("tagId", id);

        int total = blogMapper.getTotalByTagId(map);
        List<IndexVO> dataList = blogMapper.listBlogsByTagId(map);

        PaginationVO<IndexVO> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Override
    public List<ArchivesVO> listArchivesBlogs() {

        List<ArchivesVO> vo = blogMapper.listArchivesBlogs();
        return vo;
    }

    @Override
    public int countBlogs() {
        return blogMapper.getPublishedTotal(new HashMap<>());
    }
}
