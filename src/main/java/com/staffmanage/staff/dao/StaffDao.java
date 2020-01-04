package com.staffmanage.staff.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StaffDao {

    @Insert("INSERT INTO REGIST(ID,NAME,AGE,TEL,SEX,PASSWORD,department) VALUES(#{id},#{name},#{age},#{tel},#{sex},'123456',#{department})")
    void saveRegist(@Param("id") String id,@Param("name") String name,@Param("age") String age,@Param("tel") String tel,@Param("sex") String sex,@Param("department")String department);

    @Select("select * from regist where id = #{id}")
    Map<String,Object> selectUserIsExist(String id);

    @Update("update regist set password = #{password} where id = #{id}")
    void updatePassword(@Param("id") String loginName,@Param("password") String password);

    @Select("SELECT * FROM department")
    List<Map<String,Object>> selectAllDepartment();


    @Select("select * from admin where name = #{id}")
    Map<String,Object> selectadmin_UserIsExist(String id);
}
