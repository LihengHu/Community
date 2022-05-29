package com.ancientmoon.newcommunity.service;

import com.ancientmoon.newcommunity.dao.mapper.UserMapper;
import com.ancientmoon.newcommunity.entity.User;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id){
        User user = userMapper.selectById(id);
        return user;
    }
}
