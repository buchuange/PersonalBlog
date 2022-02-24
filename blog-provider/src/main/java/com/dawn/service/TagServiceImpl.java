package com.dawn.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dawn.mapper.BlogTagRelationMapper;
import com.dawn.mapper.TagMapper;
import com.dawn.model.Tag;
import com.dawn.vo.PaginationVO;
import com.dawn.vo.TopVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Service(interfaceClass = TagService.class, version = "1.0.0", timeout = 15000)
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogTagRelationMapper relationMapper;

    @Override
    public List<Tag> getAllTag() {
        return tagMapper.getAllTag();
    }

    @Transactional
    @Override
    public boolean saveTag(Tag tag) {

        boolean flag = true;

        Tag t = tagMapper.selectByName(tag.getName());

        if (t != null) {
            return false;
        }


        int count = tagMapper.insertSelective(tag);

        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Transactional
    @Override
    public boolean editTag(Tag tag) {

        boolean flag = true;

        Tag t = tagMapper.selectByName(tag.getName());

        if (t != null) {
            return false;
        }


        int count = tagMapper.updateByPrimaryKeySelective(tag);

        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Transactional
    @Override
    public Tag getTagById(Integer id) {

        Tag tag = tagMapper.selectByPrimaryKey(id);

        return tag;
    }

    @Transactional
    @Override
    public PaginationVO<Tag> listTag(int pageNum, int pageSize) {

        pageNum = (pageNum - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);

        int total = tagMapper.getTotalByCondition(map);

        List<Tag> dataList = tagMapper.getTypeListByCondition(map);

        PaginationVO<Tag> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Transactional
    @Override
    public boolean deleteTag(Integer id) {

        boolean flag = true;

        int count = tagMapper.deleteByPrimaryKey(id);

        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public Integer[] getTagIdsByBlogId(Integer id) {

        Integer[] tagIds = relationMapper.getTagIdsByBlogId(id);
        return tagIds;
    }

    @Override
    public List<TopVO> listTagTop(Integer size) {
        List<TopVO> vo = tagMapper.listTagTop(size);

        return vo;
    }

    @Override
    public String[] getTagsByBlogId(Integer id) {

        String[] tags = relationMapper.getTagsByBlogId(id);
        return tags;
    }
}
