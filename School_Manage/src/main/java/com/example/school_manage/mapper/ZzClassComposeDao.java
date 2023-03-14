package com.example.school_manage.mapper;

import com.example.school_manage.model.ZzClassCompose;
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
public interface ZzClassComposeDao {

    /**
     * 查询所有合并班级量化数据
     * @return 所有合并班级量化数据
     */

    List<ZzClassCompose> selectAllInfo();

    /**
     * 根据条件查询量化数据
     * @param className 班级名称
     * @param classTeacher 班主任
     * @return 整班量化
     */
    ZzClassCompose selectByNameInfo(@Param("className") String className, @Param("classTeacher") String classTeacher);

    /**
     * 更新合成成绩
     * @param classDiscipline
     * @param classGrilDiscipline
     * @param classManDiscipline
     * @param classHealth
     * @param classOcsd
     * @param classGrilHealth
     * @param classManHealth
     * @param classGrasp
     * @param classSpirit
     * @param classTeam
     * @param className
     * @return
     */
    int updateClassCompose(
            @Param("classDiscipline") Double classDiscipline, @Param("classGrilDiscipline") Double classGrilDiscipline,
            @Param("classManDiscipline") Double classManDiscipline, @Param("classHealth") Double classHealth,
            @Param("classOcsd") Double classOcsd, @Param("classGrilHealth") Double classGrilHealth,
            @Param("classManHealth") Double classManHealth, @Param("classGrasp") String classGrasp,
            @Param("classSpirit") String classSpirit, @Param("classTeam") Double classTeam, @Param("className") String className);
}
