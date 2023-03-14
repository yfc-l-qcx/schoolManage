package com.example.school_manage.model.teacher;

import java.io.Serializable;
import lombok.Data;

/**
 * teacher_info
 * @author 
 */
@Data
public class TeacherInfo implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 校区
     */
    private String schoolArea;

    /**
     * 年级
     */
    private String gradeName;

    /**
     * 级部
     */
    private String deptName;

    /**
     * 班级
     */
    private String className;

    /**
     * 学科
     */
    private String subjectName;

    /**
     * 电话
     */
    private String telName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 教师住址
     */
    private String address;

    private static final long serialVersionUID = 1L;
}