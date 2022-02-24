package com.dawn.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dawn.mapper.TypeMapper;
import com.dawn.model.Type;
import com.dawn.vo.PaginationVO;
import com.dawn.vo.TopVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Service(interfaceClass = TypeService.class, version = "1.0.0", timeout = 15000)
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<Type> getAllType() {
        return typeMapper.getAllType();
    }

    @Transactional
    @Override
    public boolean saveType(Type type) {

        boolean flag = true;

        Type t = typeMapper.selectByName(type.getName());

        if (t != null) {
            return false;
        }


        int count = typeMapper.insertSelective(type);

        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Transactional
    @Override
    public boolean editType(Type type) {

        boolean flag = true;

        Type t = typeMapper.selectByName(type.getName());

        if (t != null) {
            return false;
        }


        int count = typeMapper.updateByPrimaryKeySelective(type);

        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Transactional
    @Override
    public Type getTypeById(Integer id) {

        Type type = typeMapper.selectByPrimaryKey(id);

        return type;
    }

    @Transactional
    @Override
    public PaginationVO<Type> listType(int pageNum, int pageSize) {

        pageNum = (pageNum - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);

        int total = typeMapper.getTotalByCondition(map);

        List<Type> dataList = typeMapper.getTypeListByCondition(map);

        PaginationVO<Type> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Transactional
    @Override
    public boolean deleteType(Integer id) {

        boolean flag = true;

        int count = typeMapper.deleteByPrimaryKey(id);

        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public List<TopVO> listTypeTop(Integer size) {

        List<TopVO> vo = typeMapper.listTypeTop(size);
        return vo;
    }
}
