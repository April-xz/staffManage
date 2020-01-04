package com.staffmanage.staff.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Map;

@Mapper
public interface StaffManageDao {

    @Select("select * from regist")
    List<Map<String,Object>> getStaffInfo();

    @Update("update regist set name = #{name}, age = #{age}, tel = #{tel},sex = #{sex},department = #{department} where id= #{id}")
    void updateStaffInfo(@Param("id") String id, @Param("name") String name, @Param("age") String age, @Param("tel") String tel, @Param("sex") String sex, @Param("department")String department);

    @Delete("delete from regist where id = #{id}")
    void deleteStaffInfo(String id);

    @Select("select * from regist where id = #{id}")
    Map<String,Object> getStaffInfoById(String id);

    @Update("update department set departmentName = #{departmentName}, departmentActiv = #{departmentActiv}, departmentPer = #{departmentPer},departmentLeader = #{departmentLeader} where departmentId= #{departmentId}")
    void updateDepartmentInfo(@Param("departmentId") String departmentId,@Param("departmentName") String departmentName,@Param("departmentActiv") String departmentActiv,@Param("departmentPer") String departmentPer,@Param("departmentLeader") String departmentLeader);

    @Delete("delete from department where departmentId = #{departmentId}")
    void deleteDepartmentInfo(String departmentId);

    @Select("select * from department where departmentId = #{departmentId}")
    Map<String,Object> getDepartmentInfoById(String id);

    @Insert("insert into department(departmentId,departmentName,departmentActiv,departmentPer,departmentLeader) values(#{departmentId},#{departmentName},#{departmentActiv},#{departmentPer},#{departmentLeader})")
    void addDepartmentInfo(@Param("departmentId") String departmentId,@Param("departmentName") String departmentName,@Param("departmentActiv") String departmentActiv,@Param("departmentPer") String departmentPer,@Param("departmentLeader") String departmentLeader);
}
