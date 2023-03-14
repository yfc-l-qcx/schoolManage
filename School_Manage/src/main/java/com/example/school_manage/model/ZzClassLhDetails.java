package com.example.school_manage.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * zz_class_lh_details
 * @author 
 */
@Data
public class ZzClassLhDetails implements Serializable {
    /**
     * id
     */
    private Integer id;

//    @ApiModelProperty(value = "班级名字")
    private String className;

    /**
     * 班主任
     */
    private String classTeacher;

    /**
     * 违纪类型
     */
    private String classType;

    /**
     * 违纪详情
     */
    private String classDetail;

    /**
     * 上传时间
     */
    private Date classDate;

    /**
     * 星期几
     */
    private String classWeek;

    /**
     * 扣分
     */
    private Double classPoints;

    /**
     * 下载图片路径
     */
    private String classImagePath;

    /**
     * 量化类型字段名（添加）
     */
    private String classTypeKey;

    /**
     * 日期地段类型（添加）
     */

    private String classDated;
    private static final long serialVersionUID = 1L;
}