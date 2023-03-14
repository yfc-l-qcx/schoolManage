package com.example.school_manage.utils;


import com.example.school_manage.mapper.ZzClassQuantizationDao;
import com.example.school_manage.model.ZzClassQuantization;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;


/**
 *
 * 定时任务
 * @author macro
 * @date 2023/1/11
 */
@Configuration
@Log4j2
public class InitializeBJLH {

    @Autowired
    private ZzClassQuantizationDao zzClassQuantizationDao;

    @Scheduled(cron = "0 0 1 ? * MON")
    public void initialize() {
        log.info("+++++++++++定时任务已启动");
        try {
            List<ZzClassQuantization> list = zzClassQuantizationDao.selectAllInfo();
            for (ZzClassQuantization zz : list) {
                zz.setClassDiscipline(100.00);
                zz.setClassGrilDiscipline(100.00);
                zz.setClassManDiscipline(100.00);
                zz.setClassHealth(100.00);
                zz.setClassOcsd(100.00);
                zz.setClassGrilHealth(100.00);
                zz.setClassManHealth(100.00);
                zz.setClassGrasp("50");
                zz.setClassSpirit("100");
                zz.setClassTeam(50.00);
                zzClassQuantizationDao.updateInitializeBJLH(
                        zz.getClassDiscipline(),zz.getClassGrilDiscipline(),
                        zz.getClassManDiscipline(),zz.getClassHealth(),
                        zz.getClassOcsd(), zz.getClassGrilHealth(),
                        zz.getClassManHealth(), zz.getClassGrasp(),
                        zz.getClassSpirit(), zz.getClassTeam(), zz.getClassName());
            }
            log.info("+++++++++++定时任务已完成");
        } catch (Exception e) {
            log.error("========初始化任务失败" ,e);
        }

    }

}
