package com.staffmanage.staff.controll;

import com.staffmanage.staff.dao.AttendanceDao;
import com.staffmanage.staff.dao.StaffDao;
import com.staffmanage.staff.dao.performanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 评优管理
 */
@Controller
@RequestMapping("/attendanceController")
public class attendanceController {

    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private performanceDao performanceDao;

    @Autowired
    private StaffDao staffDao;


    @RequestMapping("/getAttendance")
    public ModelAndView getAttendance(String time,String department,String name) throws ParseException {
        ModelAndView view = new ModelAndView();
        String[] split = time.split(" - ");
        // 获取时间段内的全部时间
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = format.parse(split[0]);
        Date endDate = format.parse(split[1]);
        int day = (int) ((endDate.getTime() - startDate.getTime())/(1000*3600*24));
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

        List<Map<String,Object>> result = new ArrayList<>();

        // 只查询部门下的考勤状态
        if(!department.equals("---")&& name.equals("")){
            // 根据部门查询下有哪些人
            List<String> personnel =  attendanceDao.selectDepartmentPersonnel(department);
            for (String s : personnel) {
                List<Map<String, Object>> state = getState(s, allDate, split);
                result.addAll(state);
            }
        }

        // 只根据名字查询
        if (department.equals("---") && !name.equals("")){
            List<Map<String, Object>> state = getState(name, allDate, split);
            result.addAll(state);
        }

        //根据部门和员工名称查询
        if (!department.equals("---") && !name.equals("")){
            List<Map<String, Object>> state = getState(name, allDate, split);
            result.addAll(state);
        }
        view.setViewName("/attendanceFind");
        view.addObject("list",result);
        List<Map<String,Object>> departenmentList = staffDao.selectAllDepartment();
        view.addObject("departenmentList",departenmentList);
        return view;
    }

    /**
     * 转到查询考勤页面
     * @return
     */
    @RequestMapping("/routeAttendance")
    public ModelAndView getDepartmentInfo(){
        List<Map<String,Object>> departenmentList = staffDao.selectAllDepartment();
        ModelAndView v = new ModelAndView();
        v.setViewName("/attendanceFind");
        v.addObject("departenmentList",departenmentList);
        return v;
    }

    /**
     * 计算出勤率
     * 出勤率=实际出勤天数/应出勤天数×100%
     * @return
     */
    @RequestMapping("/getAttendanceRate")
    public ModelAndView getAttendanceRate(String time ,String department) throws ParseException {
        String[] split = time.split(" - ");
        // 获取时间段内的全部时间
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = format.parse(split[0]);
        Date endDate = format.parse(split[1]);
        // 应出勤天数
        double day = ((endDate.getTime() - startDate.getTime())/(1000*3600*24))+1;
        List<String> allDate = new ArrayList<>();
        if (day != 0){
            for (int i = 0;i<day;i++){
                if (i==0){
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
        List<Map<String,Object>> result = new ArrayList<>();

        // 根据部门查询下有哪些人
        List<String> personnel =  attendanceDao.selectDepartmentPersonnel(department);
        for (String s : personnel) {
            Map<String,Object> rateMap = new HashMap<>();
            double count = 0;
            List<Map<String, Object>> state = getState(s, allDate, split);
            for (Map<String, Object> stringObjectMap : state) {
                String state1 = stringObjectMap.get("state").toString();
                if (state1.equals("正常")){
                    count++;
                }
            }
            // 计算出勤率
            double i = count / day * 100;
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
            String format2 = String.format("%.2f", i);
            rateMap.put("rate",format2);
            rateMap.put("excellent",excellent);
            rateMap.put("name",s);
            result.add(rateMap);
        }

        ModelAndView vi = new ModelAndView();
        vi.setViewName("/attendanceRate");
        vi.addObject("personnel",result);
        List<Map<String,Object>> departenmentList = staffDao.selectAllDepartment();
        vi.addObject("departenmentList",departenmentList);
        return vi;
    }

    /**
     * 部门出勤率= 出勤人数÷应出勤人数×100%
     * @param time
     * @param department
     * @return
     */
    @RequestMapping("/getDepartmentAttendanceRate")
    public ModelAndView getDepartmentAttendanceRate(String time,String department) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        String[] split = time.split(" - ");
        // 获取时间段内的全部时间
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = format.parse(split[0]);
        Date endDate = format.parse(split[1]);
        // 应出勤天数
        double day = ((endDate.getTime() - startDate.getTime())/(1000*3600*24));
        List<String> allDate = new ArrayList<>();
        if (day != 0){
            for (int i = 0;i<day;i++){
                if (i==0){
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
        List<Map<String,Object>> result = new ArrayList<>();

        // 根据部门查询下有哪些人(应出勤人数)
        List<String> personnel =  attendanceDao.selectDepartmentPersonnel(department);

        List<Map<String,Object>> AllState = new ArrayList<>();

        for (String s : personnel) {
            List<Map<String, Object>> state = getState(s, allDate, split);
            AllState.addAll(state);
        }

        List<Map<String,Object>> reuslt = new ArrayList<>();
        for (String s : allDate) {
            double count = 0;
            for (Map<String, Object> stringObjectMap : AllState) {
                 // 和全部时间进行比较，符合也许要正常出勤
                if (s.equals(stringObjectMap.get("time").toString())){
                    if (stringObjectMap.get("state").equals("正常")){
                        count++;
                    }
                }
            }
            double i = count/personnel.size()*100;
            String format2 = String.format("%.2f", i);
            Map<String,Object> rate = new HashMap<>();
            rate.put("time",s);
            rate.put("rate",format2);
            reuslt.add(rate);
        }
        modelAndView.setViewName("/attendanceRate");
        List<Map<String,Object>> departenmentList = staffDao.selectAllDepartment();
        modelAndView.addObject("departenmentList",departenmentList);
        modelAndView.addObject("departmentS",reuslt);
        return modelAndView;
    }

    /**
     * 转到查询考勤率页面
     * @return
     */
    @RequestMapping("/routeAttendanceRate")
    public ModelAndView routeAttendanceRate(){
        List<Map<String,Object>> departenmentList = staffDao.selectAllDepartment();
        ModelAndView v = new ModelAndView();
        v.setViewName("/attendanceRate");
        v.addObject("departenmentList",departenmentList);
        return v;
    }


    /**
     * 获取状态
     * @param name
     * @param allDate
     * @param split
     * @return
     */
    public List<Map<String,Object>> getState(String name,List<String> allDate,String[] split){
        List<Map<String,Object>> clocklnList =  performanceDao.selectClocklnDataByDate(split[0],split[1],name);
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
            state.put("name",name);
            //state.put("department",clocklnList.get(0).get("department").toString());
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
        // 去重
        Set<String> leaveTime = new HashSet<>();
        // 查询请假信息
        List<Map<String, Object>> leave = performanceDao.selectLeaveByState(name);
        for (Map<String, Object> stringObjectMap : leave) {
            String time1 = stringObjectMap.get("time").toString();
            List<String> allTime = performanceController.getAllTime(time1);
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
        return stateList;
    }



}
