package com.staffmanage.staff.controll;

import com.staffmanage.staff.dao.StaffDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

/**
 * 员工注册功能
 */
@Controller
public class StaffController {


    @Autowired
    private StaffDao staffDao;

    public static String loginName = "";

    /**
     * 注册
     * @param id
     * @param name
     * @param age
     * @param tel
     * @param sex
     * @return
     */
    @RequestMapping("/regist")
    public ModelAndView regist(String id,String name,String age,String tel,String sex,String department){
        ModelAndView modelAndView = new ModelAndView();
        //查询是否存在
        Map<String,Object> userNmae = staffDao.selectUserIsExist(id);
        if (userNmae!=null){
            modelAndView.addObject("msg","员工编号已存在，重新输入编号！");
            modelAndView.addObject("flag",false);
            return modelAndView;
        }else {
            staffDao.saveRegist(id,name,age,tel,sex,department);
            modelAndView.addObject("msg","注册成功，默认密码为123456");
            modelAndView.addObject("flag",true);
            return modelAndView;
        }

    }


    /**
     * 登录
     * @param id
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(String id,String password){
        ModelAndView mv = new ModelAndView();
        Map<String,Object> passwordMap  = staffDao.selectUserIsExist(id);
        if (passwordMap!=null){
            String pw = passwordMap.get("password").toString();
            if (pw.equals(password)){
                mv.setViewName("/editPassowrd");
                //登录成功记录登录用户
                loginName = passwordMap.get("name").toString();
                return mv;
            }else {
                mv.addObject("msg","用户名不存在或密码错误！");
                return mv;
            }
        }else {
            mv.addObject("msg","用户名不存在或密码错误！");
            return mv;
        }
    }


    /**
     * 登录
     * @param id
     * @param password
     * @return
     */
    @RequestMapping("/admin_login")
    public ModelAndView admin_login(String id,String password){
        ModelAndView mv = new ModelAndView();
        Map<String,Object> passwordMap  = staffDao.selectadmin_UserIsExist(id);
        if (passwordMap!=null){
            String pw = passwordMap.get("password").toString();
            if (pw.equals(password)){
                mv.setView(new RedirectView("/StaffManageController/getStaffInfo"));
                return mv;
            }else {
                mv.addObject("msg","用户名不存在或密码错误！");
                return mv;
            }
        }else {
            mv.addObject("msg","用户名不存在或密码错误！");
            return mv;
        }
    }

    /**
     * 修改密码
     * @param password
     * @return
     */
    @RequestMapping("/updatePasswore")
    public ModelAndView updatePasswore(String password){
        ModelAndView modelAndView = new ModelAndView();
        staffDao.updatePassword(loginName,password);
        modelAndView.setViewName("/editPassowrd");
        return modelAndView;
    }

    /**
     * 查询全部部门
     * @param password
     * @return
     */
    @RequestMapping("/Regist")
    public ModelAndView selectAllDepartment(String password){
        ModelAndView modelAndView = new ModelAndView();
        List<Map<String,Object>> departenmentList = staffDao.selectAllDepartment();
        modelAndView.addObject("list",departenmentList);
        modelAndView.setViewName("/regist");
        return modelAndView;
    }


}
