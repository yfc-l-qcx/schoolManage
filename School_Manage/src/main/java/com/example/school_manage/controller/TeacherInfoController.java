package com.example.school_manage.controller;

import com.example.school_manage.model.teacher.TeacherInfo;
import com.example.school_manage.service.TeacherInfoService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author macro
 * @date 2023/2/8
 */
@Api(tags = "TeacherInfoController", description = "教师信息")
@RestController
@RequestMapping("/teacherInfo")
@Log4j2
public class TeacherInfoController {

    @Autowired
    private TeacherInfoService teacherInfoService;


    /**
     * 根据条件查询老师信息数据
     * @param pageSize 每页信息条数
     * @param pageNum 第几页
     * @param teacherInfo 查询条件
     * @return 查询数据
     */
    @PostMapping
    public Map<String, Object> selectByTeacherInfo( @RequestParam Integer pageSize, @RequestParam Integer pageNum,
                                                    @RequestBody TeacherInfo teacherInfo
    ){
        System.out.println("++++++pageSize++++++++" + pageSize);
        System.out.println("++++++pageNum++++++++" + pageNum);
        System.out.println("++++++teacherInfo++++++++" + teacherInfo.getClassName());
        Map<String, Object> map = teacherInfoService.selectByTeacherInfo(pageSize, pageNum, teacherInfo);
        return map;
    }

}
