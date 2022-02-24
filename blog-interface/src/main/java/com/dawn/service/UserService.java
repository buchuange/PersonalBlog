package com.dawn.service;


import com.dawn.model.User;

public interface UserService {

    /**
     * 根据用户名获取用户信息
     * @param username
     * @param password
     * @return
     */
    User checkUser(String username);

    String getType(String username);

    User getUserInfo(Integer id);
}
