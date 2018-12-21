package com.loikun.boot.service;

import com.loikun.boot.dao.UserDao;
import com.loikun.boot.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userMapper;

    public User getUser(Long id){
        return userMapper.getUser(id);
    }
}
