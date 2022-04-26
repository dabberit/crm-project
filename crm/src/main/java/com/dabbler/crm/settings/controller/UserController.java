package com.dabbler.crm.settings.controller;

import com.dabbler.crm.commons.contants.Contants;
import com.dabbler.crm.commons.entity.ReturnObject;
import com.dabbler.crm.commons.utils.DateUtils;
import com.dabbler.crm.settings.entity.User;
import com.dabbler.crm.settings.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/settings/qx/user/login.do")
    @ResponseBody
    public String login(String loginAct, String loginPwd, String isRemPwd,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user = userService.queryUserByActAndPwd(map);

        //把user对象存到session中，方便在后面的界面使用
        request.getSession().setAttribute(Contants.SESSION_USER,user);

        ReturnObject returnObject = new ReturnObject();
        if (user != null) {
            //用户名和密码正确，验证其权限是否合法
            String expireTime = user.getExpireTime();
            String nowTime = DateUtils.formatDateTime(new Date());
            if (nowTime.compareTo(expireTime) > 0) { //用户账号已过期
                returnObject.setCode(Contants.RETURN_OBJECT_FAIL);
                returnObject.setMessage("账号已过期");
            } else if (Contants.RETURN_OBJECT_FAIL.equals(user.getLockState())) { //用户被锁定
                returnObject.setCode(Contants.RETURN_OBJECT_FAIL);
                returnObject.setMessage("用户被锁定");
            } else if (!user.getAllowIps().contains(request.getRemoteAddr())){ //非法ip
                returnObject.setCode(Contants.RETURN_OBJECT_FAIL);
                returnObject.setMessage("非法ip");
            } else {
                //登陆成功
                returnObject.setCode(Contants.RETURN_OBJECT_SUCCESS);
                returnObject.setMessage("登陆成功");

                if ("true".equals(isRemPwd)) {
                    Cookie c1 = new Cookie("loginAct", user.getLoginAct());
                    Cookie c2 = new Cookie("loginPwd", user.getLoginPwd());
                    c1.setPath("/");
                    c2.setPath("/");
                    c1.setMaxAge(60 * 60 * 24 * 10);
                    c2.setMaxAge(60 * 60 * 24 * 10);
                    response.addCookie(c1);
                    response.addCookie(c2);
                }
                if ("false".equals(isRemPwd)) {
                    Cookie c1 = new Cookie("loginAct", user.getLoginAct());
                    Cookie c2 = new Cookie("loginPwd", user.getLoginAct());
                    c1.setPath("/");
                    c2.setPath("/");
                    c1.setMaxAge(0);
                    c2.setMaxAge(0);
                    response.addCookie(c1);
                    response.addCookie(c2);
                }
            }
        } else {
            //登陆失败，用户名或者密码错误
            returnObject.setMessage(Contants.RETURN_OBJECT_FAIL);
            returnObject.setMessage("账号或密码错误");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(returnObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @GetMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response, HttpSession session) {
        Cookie c1 = new Cookie("loginAct", "1");
        Cookie c2 = new Cookie("loginPwd", "1");
        c1.setPath("/");
        c2.setPath("/");
        c1.setMaxAge(0);
        c2.setMaxAge(0);
        response.addCookie(c1);
        response.addCookie(c2);

        //销毁session
        session.invalidate();
        return "redirect:/";
    }
}
