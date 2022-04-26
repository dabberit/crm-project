package com.dabbler.crm.workbench.web.mapper;

import com.dabbler.crm.workbench.web.entity.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    int deleteByPrimaryKey(String id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);

    int queryCountOfTotalRecord(Map<String,Object> map);

    int deleteActivityOfChecked(String[] ids);

    int updateActivityById(Map map);

    List<Activity> selectAllActivities();

    int insertActivityByExcel(List<Activity> activities);

    Activity selectActivityById(String id);
}