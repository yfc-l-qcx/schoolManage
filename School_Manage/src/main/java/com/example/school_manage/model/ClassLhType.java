package com.example.school_manage.model;

import java.io.Serializable;
import lombok.Data;

/**
 * class_lh_type
 * @author 
 */
@Data
public class ClassLhType implements Serializable {
    private Integer id;


    /**
     * 量化字段
     */
    private String classTypeKey;

    /**
     * 量化种类
     */
    private String classTypeValue;

    private static final long serialVersionUID = 1L;
}