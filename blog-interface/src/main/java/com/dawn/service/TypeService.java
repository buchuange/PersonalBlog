package com.dawn.service;

import com.dawn.model.Type;
import com.dawn.vo.PaginationVO;
import com.dawn.vo.TopVO;

import java.util.List;

public interface TypeService {

    boolean saveType(Type type);

    Type getTypeById(Integer id);

    PaginationVO<Type> listType(int pageNum, int pageSize);

    List<Type> getAllType();

    boolean deleteType(Integer id);

    boolean editType(Type type);

    List<TopVO> listTypeTop(Integer size);
}
