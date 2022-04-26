package com.dabbler.crm.workbench.web.controller;

import com.dabbler.crm.commons.contants.Contants;
import com.dabbler.crm.commons.entity.ReturnObject;
import com.dabbler.crm.commons.utils.DateUtils;
import com.dabbler.crm.commons.utils.HSSFUtils;
import com.dabbler.crm.commons.utils.UUIDUtils;
import com.dabbler.crm.settings.entity.User;
import com.dabbler.crm.workbench.web.entity.Activity;
import com.dabbler.crm.workbench.web.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class FileController {

    @Resource
    private ActivityService activityService;

    @GetMapping("/workbench/activity/fileDownload.do")
    public void fileDownload(HttpServletResponse response) throws IOException {

        System.out.println("执行文件下载方法");
        response.setContentType("application/octet-stream;charset=UTF-8");
        //禁止浏览器自动下载打开文件
        response.setHeader("Content-disposition", "attachment;filename=student.xls");

        ServletOutputStream os = response.getOutputStream();

        InputStream is = new FileInputStream("D:\\Data File\\xls\\first.xls");

        byte[] bys = new byte[256];

        int len = 0;
        while ((len = is.read(bys)) != -1) {
            os.write(bys, 0, len);
        }
        System.out.println("=======Download Finish=======");
        is.close();
        os.flush();
    }

    @GetMapping("/workbench/activity/exportExcelFile.do")
    public void exportExcelFile(HttpServletResponse response) throws IOException {
        List<Activity> activities = activityService.selectAllActivities();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("拥有者");
        cell = row.createCell(1);
        cell.setCellValue("项目名");
        cell = row.createCell(2);
        cell.setCellValue("开始时间");
        cell = row.createCell(3);
        cell.setCellValue("结束时间");
        cell = row.createCell(4);
        cell.setCellValue("项目资金");
        cell = row.createCell(5);
        cell.setCellValue("详情");
        cell = row.createCell(6);
        cell.setCellValue("创建时间");
        cell = row.createCell(7);
        cell.setCellValue("创建者");
        cell = row.createCell(8);
        cell.setCellValue("最后修改时间");
        cell = row.createCell(9);
        cell.setCellValue("最后修改人");
        Activity activity = null;
        for (int i = 0; i < activities.size(); i++) {
            activity = activities.get(i);
            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(activity.getOwner());
            cell = row.createCell(1);
            cell.setCellValue(activity.getName());
            cell = row.createCell(2);
            cell.setCellValue(activity.getStartDate());
            cell = row.createCell(3);
            cell.setCellValue(activity.getEndDate());
            cell = row.createCell(4);
            cell.setCellValue(activity.getCost());
            cell = row.createCell(5);
            cell.setCellValue(activity.getDescription());
            cell = row.createCell(6);
            cell.setCellValue(activity.getCreateTime());
            cell = row.createCell(7);
            cell.setCellValue(activity.getCreateBy());
            cell = row.createCell(8);
            cell.setCellValue(activity.getEditTime());
            cell = row.createCell(9);
            cell.setCellValue(activity.getEditBy());
        }

        response.setHeader("Content-disposition", "attachment;filename=ActivityData.xls");
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.flush();
    }

    @PostMapping({"/workbench/activity/importActivity.do"})
    @ResponseBody
    public Object importActivity(MultipartFile file, HttpSession session) {

        System.out.println("正在导入文件...");
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        ReturnObject ro = new ReturnObject();

        try {
            String originalFilename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            HSSFWorkbook wb = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = new Activity();
            List<Activity> list = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(DateUtils.formatDateTime(new Date()));
                activity.setCreateBy(user.getId());
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    String value = HSSFUtils.getCellValueForStr(cell);
                    if (j == 0) {
                        activity.setName(value);
                    } else if (j == 1) {
                        activity.setStartDate(value);
                    } else if (j == 2) {
                        activity.setEndDate(value);
                    } else if (j == 3) {
                        activity.setCost(value);
                    } else if (j == 4) {
                        activity.setDescription(value);
                    }
                }
                list.add(activity);
            }
            //调用service层方法
            int ret = activityService.insertActivityByExcel(list);
            if (ret > 0) {
                ro.setCode(Contants.RETURN_OBJECT_SUCCESS);
                ro.setMessage("成功添加" + ret + "条数据！");
                System.out.println("导入成功........");
            } else {
                ro.setCode(Contants.RETURN_OBJECT_FAIL);
                ro.setMessage("系统忙，请稍后再试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_FAIL);
            ro.setMessage("系统忙，请稍后再试");
        }

        return ro;
    }
}
