package com.dawn.service;

import com.dawn.model.Tag;
import com.dawn.vo.PaginationVO;
import com.dawn.vo.TopVO;

import java.util.List;

public interface TagService {

    boolean saveTag(Tag type);

    Tag getTagById(Integer id);

    PaginationVO<Tag> listTag(int pageNum, int pageSize);

    List<Tag> getAllTag();

    boolean deleteTag(Integer id);

    boolean editTag(Tag type);

    Integer[] getTagIdsByBlogId(Integer id);

    List<TopVO> listTagTop(Integer size);

    String[] getTagsByBlogId(Integer id);
}
