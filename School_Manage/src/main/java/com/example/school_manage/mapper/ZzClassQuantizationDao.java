package com.example.school_manage.mapper;

import com.example.school_manage.model.ZzClassQuantization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author aone
 */
@Repository
@Mapper
public interface ZzClassQuantizationDao {

    /**
     * 查询所有量化数据
     * @return 所有量化数据
     */

    List<ZzClassQuantization> selectAllInfo();

    /**
     * @return 返回所有班级
     */
    List<ZzClassQuantization> selectAllClass();

    /**
     * @param className 班级
     * @param classTypeKey 量化类型字段名
     * @return 某个量化分数
     */
    String selectCertainInfo(@Param("className") String className,
                             @Param("classTypeKey") String classTypeKey);

    /**
     * @param points 所扣分数
     * @param className 班级
     * @param classTypeKey 量化字段类型
     * @return
     */
    int updateCertainInfo(@Param("points") String points,
                          @Param("className") String className,
                          @Param("classTypeKey") String classTypeKey);


    /**
     * 查询班级数目
     * @return
     */
    int selectClassCount();


    /**
     * 定时每周一初始化班级量化数据
     * @param classDiscipline 教室纪律
     * @param classGrilDiscipline 女宿纪律
     * @param classManDiscipline 男宿纪律
     * @param classHealth 教室卫生
     * @param classOcsd 卫生区
     * @param classGrilHealth 女宿卫生
     * @param classManHealth 男宿卫生
     * @param classGrasp 三操
     * @param classSpirit 精神面貌
     * @param classTeam 班务
     * @param className 班级
     */
    int updateInitializeBJLH(
            @Param("classDiscipline") Double classDiscipline, @Param("classGrilDiscipline") Double classGrilDiscipline,
            @Param("classManDiscipline") Double classManDiscipline, @Param("classHealth") Double classHealth,
            @Param("classOcsd") Double classOcsd, @Param("classGrilHealth") Double classGrilHealth,
            @Param("classManHealth") Double classManHealth, @Param("classGrasp") String classGrasp,
            @Param("classSpirit") String classSpirit, @Param("classTeam") Double classTeam, @Param("className") String className);

    /**
     * 根据班级查询班主任
     * @param className 班级
     * @return
     */
    String selectByClassTeacher(String className);

}