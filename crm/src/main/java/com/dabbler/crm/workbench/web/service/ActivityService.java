package com.dabbler.crm.workbench.web.service;

import com.dabbler.crm.workbench.web.entity.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    int saveCreateActivity(Activity activity);
    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);
    int queryCountOfTotalRecord(Map<String,Object> map);
    int deleteActivityOfChecked(String[] ids);
    int updateActivityById(Map map);
    Activity selectByPrimaryKey(String id);
    List<Activity> selectAllActivities();
    int insertActivityByExcel(List<Activity> activities);
    Activity selectActivityById(String id);
}
