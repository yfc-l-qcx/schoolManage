package com.example.school_manage.service;

import com.example.school_manage.model.ClassLhType;
import com.example.school_manage.model.ZzClassCompose;
import com.example.school_manage.model.ZzClassLhDetails;
import com.example.school_manage.model.ZzClassQuantization;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * @author macro
 * {@code @date} 2022/12/15
 */
public interface ZzClassQuantizationService {
    /**
     * @return 返回所有班级量化
     */
    List<ZzClassQuantization> selectAllInfo();

    /**
     * @param zzClassLhDetails
     * @return 添加量化详细数据
     */
    Integer insertLhDetails(ZzClassLhDetails zzClassLhDetails);


    /**
     * @return 所有量化类型
     */
    List<ClassLhType> selectAllType();

    /**
     * @return 返回所有班级
     */
    List<ZzClassQuantization> selectAllClass();

    /**
     * 返回所选班级的量化明细
     * @param classType 量化类型字段
     * @param className 班级名称
     * @return 此班级的量化明细
     */
    List<ZzClassLhDetails> selectClassDetails(@Param("classType") String classType,
                                                 @Param("className") String className);

    /**
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param className 班级名称
     * @param classType 量化类型
     * @return 符合条件的量化明细
     */
    List<ZzClassLhDetails> selectClassLhDetails(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("className") String className, @Param("classType") String classType);

    /**
     * 删除填写错误的量化详情
     * @param zzClassLhDetails 量化详情
     * @return
     */
    Integer deleteDetail(@Param("zzClassLhDetails") ZzClassLhDetails zzClassLhDetails);

    /**
     * 下载图片保存图片到本地
     * @param file
     * @param fileUser
     * @return 返回图片保存路径
     */
    String savePicByFormData(MultipartFile file, String fileUser);

    /**
     * 返回所有合并班级量化
     * @param className
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
     * @param classTeacher
     * @return
     */
    List<ZzClassCompose> selectAllComposeInfo(@Param("className") String className,
                                              @Param("classDiscipline") Double classDiscipline, @Param("classGrilDiscipline") Double classGrilDiscipline, @Param("classManDiscipline") Double classManDiscipline,
                                              @Param("classHealth") Double classHealth, @Param("classOcsd") Double classOcsd, @Param("classGrilHealth") Double classGrilHealth,
                                              @Param("classManHealth") Double classManHealth, @Param("classGrasp") String classGrasp, @Param("classSpirit") String classSpirit,
                                              @Param("classTeam") Double classTeam, @Param("classTeacher") String classTeacher);
}
