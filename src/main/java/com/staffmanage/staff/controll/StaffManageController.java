package com.staffmanage.staff.controll;

import com.staffmanage.staff.dao.StaffDao;
import com.staffmanage.staff.dao.StaffManageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/StaffManageController")
public class StaffManageController {

    @Autowired
    private StaffManageDao staffManageDao;

    @Autowired
    private StaffDao staffDao;

    /**
     * 获取全部员工信息
     * @return
     */
    @RequestMapping("/getStaffInfo")
    public ModelAndView getStaffInfo(){
        List<Map<String,Object>> staffInfoList =  staffManageDao.getStaffInfo();
        ModelAndView v = new ModelAndView();
        v.setViewName("/staffManage");
        v.addObject("list",staffInfoList);
        return v;
    }

    /**
     * 获取全部部门并跳转至添加页面
     * @return
     */
    @RequestMapping("/getDepartmentAndJump")
    public ModelAndView getDepartmentAndJump(){
        ModelAndView modelAndView = new ModelAndView();
        List<Map<String,Object>> departenmentList = staffDao.selectAllDepartment();
        modelAndView.addObject("list",departenmentList);
        modelAndView.setViewName("/staffManageAdd");
        return modelAndView;
    }

    /**
     * 添加部门员工
     * @return
     */
    @RequestMapping("/addStaffInfo")
    public ModelAndView addStaffInfo(String id,String name,String age,String tel,String sex,String department){
        staffDao.saveRegist(id,name,age,tel,sex,department);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/StaffManageController/getStaffInfo"));
        return modelAndView;
    }

    /**
     * 根据id修改员工信息
     * @return
     */
    @RequestMapping("/updateStaffInfo")
    public ModelAndView updateStaffInfo(String id,String name,String age,String tel,String sex,String department){
        staffManageDao.updateStaffInfo(id,name,age,tel,sex,department);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/StaffManageController/getStaffInfo"));
        return modelAndView;
    }

    /**
     * 根据id删除员工信息
     * @return
     */
    @RequestMapping("/deleteStaffInfo")
    public ModelAndView deleteStaffInfo(String id){
        staffManageDao.deleteStaffInfo(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/StaffManageController/getStaffInfo"));
        return modelAndView;
    }

    /**
     * 根据id查询员工信息
     * @return
     */
    @RequestMapping("/getStaffInfoById")
    public ModelAndView getStaffInfoById(String id){
        Map<String,Object> staff = staffManageDao.getStaffInfoById(id);
        List<Map<String,Object>> departenmentList = staffDao.selectAllDepartment();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("map",staff);
        modelAndView.addObject("list",departenmentList);
        modelAndView.setViewName("/staffManageEdit");
        return modelAndView;
    }


    /**
     * 获取全部部门
     * @return
     */
    @RequestMapping("/getDepartmentInfo")
    public ModelAndView getDepartmentInfo(){
        List<Map<String,Object>> departenmentList = staffDao.selectAllDepartment();
        ModelAndView v = new ModelAndView();
        v.setViewName("/departmentManage");
        v.addObject("list",departenmentList);
        return v;
    }

    /**
     * 根据id修改部门信息
     * @return
     */
    @RequestMapping("/updateDepartmentInfo")
    public ModelAndView updateDepartmentInfo(String departmentId,String departmentName,String departmentActiv,String departmentPer,String departmentLeader){
        staffManageDao.updateDepartmentInfo(departmentId,departmentName,departmentActiv,departmentPer,departmentLeader);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/StaffManageController/getDepartmentInfo"));
        return modelAndView;
    }

    /**
     * 根据id删除部门信息
     * @return
     */
    @RequestMapping("/deleteDepartmentInfo")
    public ModelAndView deleteDepartmentInfo(String departmentId){
        staffManageDao.deleteDepartmentInfo(departmentId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/StaffManageController/getDepartmentInfo"));
        return modelAndView;
    }

    /**
     * 根据id查询部门信息
     * @return
     */
    @RequestMapping("/getDepartmentInfoById")
    public ModelAndView getDepartmentInfoById(String id){
        Map<String,Object> staff = staffManageDao.getDepartmentInfoById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("map",staff);
        modelAndView.setViewName("/departmentManageEdit");
        return modelAndView;
    }

    /**
     * 添加部门
     * @return
     */
    @RequestMapping("/addDepartmentInfo")
    public ModelAndView addDepartmentInfo(String departmentId,String departmentName,String departmentActiv,String departmentPer,String departmentLeader){
        staffManageDao.addDepartmentInfo(departmentId,departmentName,departmentActiv,departmentPer,departmentLeader);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/StaffManageController/getDepartmentInfo"));
        return modelAndView;
    }



}
