package com.dabbler.crm.settings.mapper;

import com.dabbler.crm.settings.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 通过账号密码查询用户所有信息
     * @param map
     * @return
     */
    User selectUserByActAndPwd(Map map);

    /**
     * 查询所有用户
     * @return
     */
    List<User> selectAllUser();
}