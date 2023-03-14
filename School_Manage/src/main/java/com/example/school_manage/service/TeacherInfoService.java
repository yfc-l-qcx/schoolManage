package com.example.school_manage.service;

import com.example.school_manage.model.teacher.TeacherInfo;

import java.util.List;
import java.util.Map;

/**
 * @author aone
 */
public interface TeacherInfoService {
    /**
     * 根据条件查询老师信息数据
     * @param pageSize 每页信息条数
     * @param pageNum 第几页
     * @param teacherInfo 查询条件
     * @return 查询数据
     */
    Map<String, Object> selectByTeacherInfo(Integer pageSize, Integer pageNum, TeacherInfo teacherInfo);
}
