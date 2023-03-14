package com.example.school_manage.service.impl;

import com.example.school_manage.mapper.teacher.TeacherInfoDao;
import com.example.school_manage.model.teacher.TeacherInfo;
import com.example.school_manage.service.TeacherInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author macro
 * @date 2023/2/8
 */
@Log4j2
@Service
public class TeacherInfoServiceImpl implements TeacherInfoService {

    @Autowired
    private TeacherInfoDao teacherInfoDao;
    /**
     * 根据条件查询老师信息数据
     * @param pageSize 每页信息条数
     * @param pageNum 第几页
     * @param teacherInfo 查询条件
     * @return 查询数据
     */
    @Override
    public Map<String, Object> selectByTeacherInfo(Integer pageSize, Integer pageNum, TeacherInfo teacherInfo) {
        System.out.println("=========pageSize=========" + pageSize);
        System.out.println("=========pageNum=========" + pageNum);
        pageNum = (pageNum - 1) * pageSize;
        System.out.println("=========getClassName=========" + teacherInfo.getClassName());
        System.out.println("=========getSchoolArea=========" + teacherInfo.getSchoolArea());
        System.out.println("=========getDeptName=========" + teacherInfo.getDeptName());
        System.out.println("=========getGradeName=========" + teacherInfo.getGradeName());
        System.out.println("=========getTeacherName=========" + teacherInfo.getTeacherName());
        List<TeacherInfo> listAll = teacherInfoDao.selectByTeacherInfo(pageSize, pageNum, teacherInfo.getClassName(),
                teacherInfo.getSchoolArea(), teacherInfo.getDeptName(), teacherInfo.getGradeName(), teacherInfo.getTeacherName());
        Integer count = teacherInfoDao.selectAllCount();
        Map<String, Object> map = new HashMap<>();
        map.put("data", listAll);
        map.put("count", count);
        System.out.println("+++++listAll+++++" + listAll);
        System.out.println("+++++map+++++" + map);
        return map;
    }
}
