package com.dabbler.crm.settings.service;

import com.dabbler.crm.settings.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User queryUserByActAndPwd(Map map);

    List<User> selectAllUser();
}
