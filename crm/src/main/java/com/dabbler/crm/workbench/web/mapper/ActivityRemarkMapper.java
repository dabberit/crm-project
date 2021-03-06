package com.dabbler.crm.workbench.web.mapper;

import com.dabbler.crm.workbench.web.entity.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(ActivityRemark record);

    int insertSelective(ActivityRemark record);

    ActivityRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActivityRemark record);

    int updateByPrimaryKey(ActivityRemark record);

    List<ActivityRemark> selectActivityRemarkById(String activity_id);

    int insertActivityRemark(ActivityRemark remark);

    int deleteActivityRemarkById(String id);

    int updateActivityRemarkById(ActivityRemark remark);
}