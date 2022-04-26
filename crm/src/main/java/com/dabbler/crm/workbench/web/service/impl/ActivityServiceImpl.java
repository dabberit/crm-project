package com.dabbler.crm.workbench.web.service.impl;

import com.dabbler.crm.workbench.web.entity.Activity;
import com.dabbler.crm.workbench.web.mapper.ActivityMapper;
import com.dabbler.crm.workbench.web.service.ActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityMapper activityMapper;

    @Transactional
    @Override
    public int saveCreateActivity(Activity activity) {
        return activityMapper.insert(activity);
    }

    @Override
    public int queryCountOfTotalRecord(Map<String, Object> map) {
        return activityMapper.queryCountOfTotalRecord(map);
    }

    @Override
    public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {
        return activityMapper.queryActivityByConditionForPage(map);
    }

    @Override
    public int deleteActivityOfChecked(String[] ids) {
        return activityMapper.deleteActivityOfChecked(ids);
    }

    @Override
    public int updateActivityById(Map map) {
        return activityMapper.updateActivityById(map);
    }

    @Override
    public Activity selectByPrimaryKey(String id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Activity> selectAllActivities() {
        return activityMapper.selectAllActivities();
    }

    @Override
    public int insertActivityByExcel(List<Activity> activities) {
        return activityMapper.insertActivityByExcel(activities);
    }

    @Override
    public Activity selectActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }
}
