package com.example.school_manage.model;

import lombok.Data;
import org.mybatis.spring.annotation.MapperScan;

import java.io.Serializable;

/**
 * zz_class_quantization
 * @author 
 */
@Data
@MapperScan(basePackages = "com.example.school_manage.mapper")
public class ZzClassCompose implements Serializable {
    private Integer id;

    /**
     * 班级
     */
    private String className;

    /**
     * 教室纪律
     */
    private Double classDiscipline;

    /**
     * 女宿纪律
     */
    private Double classGrilDiscipline;

    /**
     * 男宿纪律
     */
    private Double classManDiscipline;

    /**
     * 教室卫生
     */
    private Double classHealth;

    /**
     * 卫生区
     */
    private Double classOcsd;

    /**
     * 女宿卫生
     */
    private Double classGrilHealth;

    /**
     * 男宿卫生
     */
    private Double classManHealth;

    /**
     * 三操
     */
    private String classGrasp;

    /**
     * 精神面貌
     */
    private String classSpirit;

    /**
     * 班务
     */
    private Double classTeam;

    /**
     * 行为量化
     */
    private Double classQuantization;

    /**
     * 排名
     */
    private Integer classRanking;

    /**
     * 班主任
     */
    private String classTeacher;

    /**
     * 备注
     */
    private String classRemark;

    private static final long serialVersionUID = 1L;

}