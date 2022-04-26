package com.dabbler.crm.settings.service.impl;

import com.dabbler.crm.settings.entity.User;
import com.dabbler.crm.settings.mapper.UserMapper;
import com.dabbler.crm.settings.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User queryUserByActAndPwd(Map map) {
        return userMapper.selectUserByActAndPwd(map);
    }

    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }
}
