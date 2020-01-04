package com.staffmanage.staff.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AttendanceDao {

    @Select("select name from regist where department = #{department}")
    List<String> selectDepartmentPersonnel(String department);
}
