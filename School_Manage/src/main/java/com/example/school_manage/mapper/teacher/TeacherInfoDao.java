package com.example.school_manage.mapper.teacher;

import com.example.school_manage.model.teacher.TeacherInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教师信息dao
 * @author aone
 */
@Repository
@Mapper
public interface TeacherInfoDao {
    TeacherInfo selectByPrimaryKey(Integer id);

    /**
     * 根据条件查询教师数据
     * @param pageSize
     * @param pageNum
     * @param className
     * @param schoolArea
     * @param deptName
     * @param gradeName
     * @param teacherName
     * @return
     */
    List<TeacherInfo> selectByTeacherInfo(Integer pageSize, Integer pageNum, String className, String schoolArea,
                                          String deptName, String gradeName, String teacherName);

    /**
     * 返回教师条数
     * @return
     */
    Integer selectAllCount();
}