package com.dawn.mapper;

import com.dawn.model.Type;
import com.dawn.vo.TopVO;

import java.util.List;
import java.util.Map;

public interface TypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    int insertSelective(Type record);

    Type selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);

    int getTotalByCondition(Map<String, Object> map);

    List<Type> getTypeListByCondition(Map<String, Object> map);

    Type selectByName(String name);

    List<Type> getAllType();

    List<TopVO> listTypeTop(Integer size);
}