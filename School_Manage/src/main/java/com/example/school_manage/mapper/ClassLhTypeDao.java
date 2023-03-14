package com.example.school_manage.mapper;
import org.apache.ibatis.annotations.Param;

import com.example.school_manage.model.ClassLhType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 量化类型
 * @author aone
 */
@Repository
@Mapper
public interface ClassLhTypeDao {

    /**
     * @return 获得所有量化类型
     */
    List<ClassLhType> selectAllType();

    /**
     * @param classTypeValue 量化类型
     * @return 量化类型字段
     */
    String selectByClassTypeValue(@Param("classTypeValue") String classTypeValue);
}