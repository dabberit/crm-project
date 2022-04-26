package com.dabbler.crm.workbench.web.service;

import com.dabbler.crm.workbench.web.entity.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {
    List<ActivityRemark> selectActivityRemarkById(String activity_id);

    int insertActivityRemark(ActivityRemark remark);

    int deleteActivityRemarkById(String id);
}
