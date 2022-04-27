package com.dabbler.crm.workbench.web.controller;

import com.dabbler.crm.commons.contants.Contants;
import com.dabbler.crm.commons.entity.ReturnObject;
import com.dabbler.crm.commons.utils.DateUtils;
import com.dabbler.crm.settings.entity.User;
import com.dabbler.crm.workbench.web.entity.ActivityRemark;
import com.dabbler.crm.workbench.web.service.ActivityRemarkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ActivityRemarkController {

    @Resource
    private ActivityRemarkService activityRemarkService;

    @PostMapping("/workbench/activity/deleteActivityRemarkById.do")
    @ResponseBody
    public Object deleteActivityRemarkById(String id) {

        ReturnObject ro = new ReturnObject();
        try{
            int i = activityRemarkService.deleteActivityRemarkById(id);
            if (i > 0) {
                ro.setCode(Contants.RETURN_OBJECT_SUCCESS);
            } else {
                ro.setCode(Contants.RETURN_OBJECT_FAIL);
                ro.setMessage(Contants.RETURN_ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_FAIL);
            ro.setMessage(Contants.RETURN_ERROR_MESSAGE);
        }
        return ro;
    }

    @PostMapping("/workbench/activity/updateActivityRemarkById.do")
    @ResponseBody
    public Object updateActivityRemarkById(HttpSession session,ActivityRemark remark) {
        remark.setEditTime(DateUtils.formatDateTime(new Date()));
        remark.setEditBy(( (User) session.getAttribute(Contants.SESSION_USER)).getId());
        remark.setEditFlag(Contants.REMARK_EDIT_YES);

        ReturnObject ro = new ReturnObject();
        try {
            int res = activityRemarkService.updateActivityRemarkById(remark);
            if (res > 0) {
                ro.setCode(Contants.RETURN_OBJECT_SUCCESS);
                ro.setOtherData(remark);
            } else {
                ro.setCode(Contants.RETURN_OBJECT_FAIL);
                ro.setMessage(Contants.RETURN_ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_FAIL);
            ro.setMessage(Contants.RETURN_ERROR_MESSAGE);
        }

        return ro;
    }

}
