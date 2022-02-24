package com.dawn.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dawn.mapper.UserMapper;
import com.dawn.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = UserService.class, version = "1.0.0", timeout = 15000)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public String getType(String username) {

        // 提升系统性能，用户体验提升

        //设置 redisTemplate 对象 key 的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        /*
           首先去redis缓存中查询，如果有：直接使用
           如果没有：去数据库查询并存放到redis缓存中
         */
        String type = (String) redisTemplate.opsForValue().get("type");

        // 判断是否有值
        if (null == type) {

            // 去数据库查询
            User user = userMapper.findByUsername(username);
            type = user.getType();


            // 并存放到redis缓存中
            redisTemplate.opsForValue().set("type", type, 30, TimeUnit.MINUTES);
        }


        return type;
    }

    @Override
    public User checkUser(String username) {

        User user = userMapper.findByUsername(username);

        return user;
    }

    @Override
    public User getUserInfo(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
