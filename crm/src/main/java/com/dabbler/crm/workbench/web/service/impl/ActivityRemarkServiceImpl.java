package com.dabbler.crm.workbench.web.service.impl;

import com.dabbler.crm.workbench.web.entity.ActivityRemark;
import com.dabbler.crm.workbench.web.mapper.ActivityRemarkMapper;
import com.dabbler.crm.workbench.web.service.ActivityRemarkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Resource
    private ActivityRemarkMapper activityRemarkMapper;

    @Override
    public List<ActivityRemark> selectActivityRemarkById(String activity_id) {
        return activityRemarkMapper.selectActivityRemarkById(activity_id);
    }

    @Override
    public int insertActivityRemark(ActivityRemark remark) {
        return activityRemarkMapper.insertActivityRemark(remark);
    }

    @Override
    public int deleteActivityRemarkById(String id) {
        return activityRemarkMapper.deleteActivityRemarkById(id);
    }
}
