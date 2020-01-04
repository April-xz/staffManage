package com.staffmanage.staff.controll;

import com.staffmanage.staff.dao.AttendanceDao;
import com.staffmanage.staff.dao.performanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

/**
 * 绩效考核管理功能
 */
@Controller
public class performanceController {

    @Autowired
    private performanceDao performanceDao;

    @Autowired
    private AttendanceDao attendanceDao;

    /**
     * 打卡
     * @return
     */
    @RequestMapping("clockln")
    @ResponseBody
    public String clockin(){
        ModelAndView mv = new ModelAndView();
        String loginName = StaffController.loginName;
        long timeDate = new Date().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
        String format = simpleDateFormat.format(timeDate);
        String[] split = format.split("-");
        String  date = split[0];
        String  time = split[1];
        // 查询员工所属部门
        Map<String,Object> staff =  performanceDao.selelctParentmentByStaff(loginName);
        // 保存打卡
        performanceDao.saveClockln(loginName,time,date,staff.get("department").toString());
        return "success";
    }

    /**
     * 查询打卡
     * @param time
     * @return
     * @throws ParseException
     */
    @RequestMapping("/getStaffClocklnState")
    public ModelAndView getStaffClocklnState(String time) throws ParseException {
        ModelAndView view = new ModelAndView();
        String[] split = time.split(" - ");
        // 获取时间段内的全部时间
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = format.parse(split[0]);
        Date endDate = format.parse(split[1]);
        int day = (int) ((endDate.getTime() - startDate.getTime())/(1000*3600*24));
        // 用来计算评优 应该出勤天数
        double ycq =  ((endDate.getTime() - startDate.getTime())/(1000*3600*24))+1;
        // 一共查询那几天
        List<String> allDate = new ArrayList<>();
        if (day != 0){
            for (int i = 0;i<day;i++){
                if (i==0){
                    System.out.println(format.format(startDate));
                    allDate.add(format.format(startDate));
                }
                Date date = new Date(startDate.getTime() + (1000 * 3600 * 24));
                allDate.add(format.format(date));
                startDate = date;
            }
        }else {
            // 一天
            allDate.add(split[0]);
        }

        // 查询规定时间内的考勤数据
        List<Map<String,Object>> clocklnList =  performanceDao.selectClocklnDataByDate(split[0],split[1],StaffController.loginName);
        Map<String,Object> map = new HashMap<>();
        map.put("date","date");
        map.put("time","time");
        clocklnList.add(map);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        // 存只有第一次和最后一次打卡
        List<Map<String,Object>> dateList = new ArrayList<>();
        String dayS="";
        String start = null,end = null;
        for (int i = 0; i < clocklnList.size(); i++) {
            String timeS = clocklnList.get(i).get("date").toString();
            //判断是否到了下一天
            if (!dayS.equals(timeS)) {
                if (start!=null&&end!=null) {
                    Map<String,Object> dateMap = new HashMap<>();
                    dateMap.put("day",dayS);
                    dateMap.put("start",start);
                    dateMap.put("end",end);
                    dateList.add(dateMap);
                }
                //把每天第一条数据当做开始和结束时间
                dayS = clocklnList.get(i).get("date").toString();
                start = clocklnList.get(i).get("time").toString();;
                end = clocklnList.get(i).get("time").toString();
            }
            //只对结束时间做更新
            end = clocklnList.get(i).get("time").toString();
        }

        List<Map<String,Object>> stateList = new ArrayList<>();
        // 循环判断状态
        for (String s : allDate) {
            Map<String,Object> state = new HashMap<>();
            state.put("time",s);
            boolean flag = false;
            for (Map<String, Object> stringObjectMap : dateList) {

                if (s.equals(stringObjectMap.get("day").toString())){
                    flag = true;
                    boolean f = false;
                    // 有相同的天 取出开始时间和结束时间进行比较
                    String start1 = stringObjectMap.get("start").toString();
                    String end1 = stringObjectMap.get("end").toString();
                    // 正常状态
                    if (Integer.valueOf(start1.split(":")[0])< 8 && Integer.valueOf(end1.split(":")[0]) >= 17 ){
                        state.put("state","正常");
                        f = true;
                    }
                    // 迟到
                    if (Integer.valueOf(start1.split(":")[0])>= 8 && Integer.valueOf(end1.split(":")[0]) >= 17 && Integer.valueOf(start1.split(":")[0])<= 12){
                        state.put("state","迟到");
                        f = true;
                    }
                    // 早退
                    if (Integer.valueOf(start1.split(":")[0])< 8 && Integer.valueOf(end1.split(":")[0]) < 17 && Integer.valueOf(end1.split(":")[0]) >= 12){
                        state.put("state","早退");
                        f = true;
                    }
                    // 不在工作范围时间内打卡
                    if (!f){
                        state.put("state","异常");
                    }
                }
            }
            // 循环请假集合判断是否请假

            // 没有匹配时间 旷工
            if (!flag){
                state.put("state","旷工");
            }
            stateList.add(state);
        }
        double count = 0;
        for (Map<String, Object> stringObjectMap : stateList) {
            if (stringObjectMap.get("state").toString().equals("正常")){
                count++;
            }
        }
        double i = count / ycq * 100;
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
        String format1 = df.format(i);
        String excellent = "";
        if (i>85){
            excellent = "A";
        }
        if (i>=70 && i<=85){
            excellent = "B";
        }
        if (i>=50 && i<=70){
            excellent = "C";
        }
        if (i<50 ){
            excellent = "D";
        }
        // 去重
        Set<String> leaveTime = new HashSet<>();
        // 查询请假信息
        List<Map<String, Object>> leave = performanceDao.selectLeaveByState(StaffController.loginName);
        for (Map<String, Object> stringObjectMap : leave) {
            String time1 = stringObjectMap.get("time").toString();
            List<String> allTime = getAllTime(time1);
            for (String s : allTime) {
                leaveTime.add(s);
            }
        }
        // 判断是否请假
        for (Map<String, Object> stringObjectMap : stateList) {
            String time1 = stringObjectMap.get("time").toString();
            for (String s : leaveTime) {
                if (time1.equals(s)){
                    stringObjectMap.put("state","请假");
                }
            }
        }
        view.addObject("rate",excellent);
        view.addObject("rate1",String.format("%.2f", i));
        view.setViewName("clockIn");
        view.addObject("list",stateList);
        return view;
    }


    /**
     * 请假
     * @param file
     * @param type
     * @param info
     * @return
     * @throws Exception
     * @throws IOException
     */
    @RequestMapping("/leave")
    public ModelAndView leave( @RequestParam("file") MultipartFile file, String type ,String info,String time) throws Exception, IOException {
        //获取整个文件名字包括后缀
        String filename = file.getOriginalFilename();
        System.out.println(this.getClass().getResource("/").getPath());
        //存储路径
        String classpath = this.getClass().getResource("/").getPath()+"static/img/"+filename;
        File f = new File(classpath);
        //保存图片
        file.transferTo(f);
        performanceDao.saveLeave("img/"+filename,type,info,StaffController.loginName,time);
        List<Map<String,Object>> leaveState = performanceDao.selectLeave(StaffController.loginName);
        ModelAndView mv = new ModelAndView();
        mv.addObject("list",leaveState);
        mv.setViewName("leave");
        return mv;
    }

    /**
     * 查询请假状态
     * @return
     */
    @RequestMapping("/getLeaveState")
    public ModelAndView getLeaveState(){
        List<Map<String,Object>> leaveState = performanceDao.selectLeave(StaffController.loginName);
        ModelAndView mv = new ModelAndView();
        mv.addObject("list",leaveState);
        mv.setViewName("leave");
        return mv;
    }

    /**
     * 代办（领导审批请假）
     * @return
     */
    @RequestMapping("/getAllLeaveState")
    public ModelAndView getAllLeaveState(){
        ModelAndView mv = new ModelAndView();
        // 根据登录用户判断是否是领导,如果是在哪个部门
        String loginName = StaffController.loginName;
        // 根据登录人员名称查询
        Map<String,Object> departmentLeader =  performanceDao.selectDepartmentLeaderByLoginName(loginName);
        if (departmentLeader == null){
            // 不是领导
            mv.addObject("flag",false);
            mv.setViewName("leaveApprove.jsp");
            return mv;
        }else {
            // 是领导
            String departmentName = departmentLeader.get("departmentName").toString();
            // 获取全部部门人员
            List<String> strings = attendanceDao.selectDepartmentPersonnel(departmentName);
            String s = "";
            for (String string : strings) {
                s+="'"+string+"'"+",";
            }
            // 获取全部请假信息
            List<Map<String,Object>> allLeave = performanceDao.selectAllLeave(s.substring(0,s.length()-1));
            mv.addObject("list",allLeave);
            mv.addObject("flag",true);
            mv.setViewName("leaveApprove");
            return mv;
        }
    }


    /**
     * 文件下载
     * @param request
     * @param path
     * @return
     * @throws IOException
     */
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request,String path) throws IOException {
        String classpath = this.getClass().getResource("/").getPath()+"static/"+path;
        File file = new File(classpath);
        byte[] body = null;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        is.read(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        return entity;
    }


    /**
     * 更新请假状态
     * @param state
     * @param id
     * @return
     */
    @RequestMapping("/updateState")
    public ModelAndView updateState(String state ,String id){
        ModelAndView modelAndView = new ModelAndView();
        performanceDao.updateState(state,id);
        modelAndView.setView(new RedirectView("/getAllLeaveState"));
        return modelAndView;
    }


    /**
     * 获取一共多少天
     * @param time
     * @return
     */
    public static List<String> getAllTime(String time){
        String[] split = time.split(" - ");
        // 获取时间段内的全部时间
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = null;
        try {
            startDate = format.parse(split[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null;
        try {
            endDate = format.parse(split[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day = (int) ((endDate.getTime() - startDate.getTime())/(1000*3600*24));
        // 用来计算评优 应该出勤天数
        double ycq =  ((endDate.getTime() - startDate.getTime())/(1000*3600*24))+1;
        // 一共查询那几天
        List<String> allDate = new ArrayList<>();
        if (day != 0){
            for (int i = 0;i<day;i++){
                if (i==0){
                    System.out.println(format.format(startDate));
                    allDate.add(format.format(startDate));
                }
                Date date = new Date(startDate.getTime() + (1000 * 3600 * 24));
                allDate.add(format.format(date));
                startDate = date;
            }
        }else {
            // 一天
            allDate.add(split[0]);
        }
        return allDate;
    }

}
