package com.dabbler.crm.workbench.web.controller;

import com.dabbler.crm.commons.contants.Contants;
import com.dabbler.crm.commons.entity.ReturnObject;
import com.dabbler.crm.commons.utils.DateUtils;
import com.dabbler.crm.commons.utils.UUIDUtils;
import com.dabbler.crm.settings.entity.User;
import com.dabbler.crm.settings.service.UserService;
import com.dabbler.crm.workbench.web.entity.Activity;
import com.dabbler.crm.workbench.web.entity.ActivityRemark;
import com.dabbler.crm.workbench.web.service.ActivityRemarkService;
import com.dabbler.crm.workbench.web.service.ActivityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class ActivityController {

    @Resource
    private UserService userService;

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivityRemarkService activityRemarkService;

    @GetMapping("/workbench/activity/index.do")
    public String index(Model model) {
        List<User> users = userService.selectAllUser();
        model.addAttribute("users", users);
        return "workbench/activity/index";
    }

    @PostMapping("/workbench/activity/save.do")
    @ResponseBody
    public String save(Activity activity, HttpSession session) throws JsonProcessingException {

        User user = (User) session.getAttribute(Contants.SESSION_USER);
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());

        ReturnObject ro = new ReturnObject();
        try {
            int count = activityService.saveCreateActivity(activity);
            if (count > 0) {
                ro.setCode(Contants.RETURN_OBJECT_SUCCESS);
            } else {
                ro.setCode(Contants.RETURN_OBJECT_FAIL);
                ro.setMessage("我们的系统出错了，但我们不想告诉你");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper obm = new ObjectMapper();
        String json = obm.writeValueAsString(ro);
        return json;
    }

    @PostMapping("/workbench/activity/queryActivityByConditionForPage.do")
    @ResponseBody
    public String queryActivityByConditionForPage(String name, String owner, String startDate, String endDate,
                                                  int beginNo, int pageSize) throws JsonProcessingException {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        paramMap.put("owner", owner);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("beginNo", (beginNo - 1) * pageSize);
        paramMap.put("pageSize", pageSize);

        List<Activity> activityList = activityService.queryActivityByConditionForPage(paramMap);
        int totalRows = activityService.queryCountOfTotalRecord(paramMap);

        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("activityList", activityList);
        retMap.put("totalRows", totalRows);
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(retMap);
        return json;
    }

    @PostMapping("/workbench/activity/deleteActivityOfChecked.do")
    @ResponseBody
    public Object deleteActivityOfChecked(String[] id) {

        System.out.println("id = " + Arrays.toString(id));
        ReturnObject ro = new ReturnObject();
        try {
            int ret = activityService.deleteActivityOfChecked(id);
            if (ret > 0) {
                ro.setCode(Contants.RETURN_OBJECT_SUCCESS);
            } else {
                ro.setCode(Contants.RETURN_OBJECT_FAIL);
                ro.setMessage("系统忙，请稍后重试！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_FAIL);
            ro.setMessage("系统忙，请稍后重试！");
        }
        return ro;
    }

    @PostMapping("/workbench/activity/selectByPrimaryKey.do")
    @ResponseBody
    public Object selectByPrimaryKey(String id) {
        Activity activity = null;
        System.out.println("id = " + id);
        try {
            activity = activityService.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activity;
    }

    @PostMapping("/workbench/activity/updateActivityById.do")
    @ResponseBody
    public Object updateActivityById(HttpSession session, String id, String owner, String name, String startDate, String endDate, String cost, String describe) {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put("id", id);
        queryMap.put("owner", owner);
        queryMap.put("name", name);
        queryMap.put("startDate", startDate);
        queryMap.put("endDate", endDate);
        queryMap.put("cost", cost);
        queryMap.put("description", describe);
        queryMap.put("editTime", DateUtils.formatDateTime(new Date()));
        queryMap.put("editBy", ((User) session.getAttribute(Contants.SESSION_USER)).getId());

        ReturnObject ro = new ReturnObject();
        try {
            int res = activityService.updateActivityById(queryMap);
            if (res == 1) {
                ro.setCode(Contants.RETURN_OBJECT_SUCCESS);
            } else {
                ro.setCode(Contants.RETURN_OBJECT_FAIL);
                ro.setMessage("系统忙，请稍后再试！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ro;
    }

    @GetMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(Model model, String id) {
        Activity activity = activityService.selectActivityById(id);
        List<ActivityRemark> activityRemarks = activityRemarkService.selectActivityRemarkById(id);
        model.addAttribute("activity",activity);
        model.addAttribute("activityRemarks",activityRemarks);

        return "workbench/activity/detail";
    }

    @PostMapping("/workbench/activity/insertActivityRemark.do")
    @ResponseBody
    public Object insertActivityRemark(HttpSession session,ActivityRemark remark) {
        String uuid = UUIDUtils.getUUID();
        remark.setId(uuid);
        remark.setCreateBy(((User) session.getAttribute(Contants.SESSION_USER)).getId());
        remark.setCreateTime(DateUtils.formatDateTime(new Date()));
        remark.setEditFlag(Contants.REMARK_EDIT_NO);

        ReturnObject ro = new ReturnObject();
        try{
            int res = activityRemarkService.insertActivityRemark(remark);
            if (res > 0) {
                ro.setCode(Contants.RETURN_OBJECT_SUCCESS);
                ro.setMessage("添加成功");
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
