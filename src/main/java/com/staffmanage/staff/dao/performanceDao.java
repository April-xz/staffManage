package com.staffmanage.staff.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface performanceDao {

    @Select("select * from regist where name = #{loginName}")
    Map<String,Object> selelctParentmentByStaff(String loginName);

    @Insert("insert into clockln(staffName,time,date,department) values(#{staffName},#{time},#{date},#{department})")
    void saveClockln(@Param("staffName") String loginName,@Param("time") String time,@Param("date") String date,@Param("department") String department);

    @Select("select * from clockln where  date >= #{start}    and   date <= #{end} and staffName = #{staff}")
    List<Map<String,Object>> selectClocklnDataByDate(@Param("start")String s,@Param("end") String s1,@Param("staff") String staff);

    @Insert("insert into leaves(staffName,type,info,imgPath,state,time) values(#{staffName},#{type},#{info},#{imgPath},'待审核',#{time})")
    void saveLeave(@Param("imgPath") String s,@Param("type") String type,@Param("info") String info,@Param("staffName") String loginName,@Param("time") String time);

    @Select("select * from leaves where staffName = #{loginName}")
    List<Map<String,Object>> selectLeave(String loginName);

    @Select("select * from leaves where staffName = #{loginName} and state = '通过'")
    List<Map<String,Object>> selectLeaveByState(String loginName);

    @Select("select * from department where departmentLeader = #{loginName}")
    Map<String,Object> selectDepartmentLeaderByLoginName(String loginName);

    @Select("select * from leaves WHERE staffName IN (${substring})")
    List<Map<String,Object>> selectAllLeave(@Param("substring") String substring);

    @Update("update leaves set state = #{state} where id = #{id}")
    void updateState(@Param("state")String state,@Param("id") String id);
}
