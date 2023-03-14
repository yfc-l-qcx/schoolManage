package com.example.school_manage.mapper;

import com.example.school_manage.model.ZzClassLhDetails;
import com.example.school_manage.model.ZzClassQuantization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 添加量化的详情
 * @author aone
 */
@Repository
@Mapper
public interface ZzClassLhDetailsDao {

    int deleteByPrimaryKey(Integer id);

    int insert(ZzClassLhDetails record);

    /**
     * 添加量化
     * @param record
     * @return
     */
    int insertSelective( ZzClassLhDetails record);

    ZzClassLhDetails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZzClassLhDetails record);

    int updateByPrimaryKey(ZzClassLhDetails record);

    /**
     * 返回所选班级的量化明细
     * @param classType      量化类型字段
     * @param className      班级名称
     * @return 此班级的量化明细
     */
    List<ZzClassLhDetails> selectClassDetails(@Param("classType") String classType,
                                                 @Param("className") String className);

    /**
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param className 班级名称
     * @param classType 量化类型
     * @return 所有符合条件的量化明细
     */
    List<ZzClassLhDetails> selectClassLhDetails(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("className") String className, @Param("classType") String classType);


}