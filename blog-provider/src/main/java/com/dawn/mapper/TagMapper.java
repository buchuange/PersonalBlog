package com.dawn.mapper;

import com.dawn.model.Tag;
import com.dawn.model.Type;
import com.dawn.vo.TopVO;

import java.util.List;
import java.util.Map;

public interface TagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    int getTotalByCondition(Map<String, Object> map);

    List<Tag> getTypeListByCondition(Map<String, Object> map);

    Tag selectByName(String name);

    List<Tag> getAllTag();

    List<TopVO> listTagTop(Integer size);
}