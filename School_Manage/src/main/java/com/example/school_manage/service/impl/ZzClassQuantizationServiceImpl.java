package com.example.school_manage.service.impl;

import com.example.school_manage.mapper.ClassLhTypeDao;
import com.example.school_manage.mapper.ZzClassComposeDao;
import com.example.school_manage.mapper.ZzClassLhDetailsDao;
import com.example.school_manage.mapper.ZzClassQuantizationDao;
import com.example.school_manage.model.ClassLhType;
import com.example.school_manage.model.ZzClassCompose;
import com.example.school_manage.model.ZzClassLhDetails;
import com.example.school_manage.model.ZzClassQuantization;
import com.example.school_manage.service.ZzClassQuantizationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.*;

/**
 *
 * @author macro
 * {@code @date} 2022/12/15
 */
@Log4j2
@Service
public class ZzClassQuantizationServiceImpl implements ZzClassQuantizationService {

    @Autowired
    private ZzClassQuantizationDao zzClassQuantizationDao;
    @Autowired
    private ZzClassLhDetailsDao zzClassLhDetailsDao;
    @Autowired
    private ClassLhTypeDao classLhTypeDao;
    @Autowired
    private ZzClassComposeDao zzClassComposeDao;

    /**
     * 查询所有班级量化 并计算总分和名次、排序
     * @return 返回所有班级量化
     */
    @Override
    public List<ZzClassQuantization> selectAllInfo() {
        int count = zzClassQuantizationDao.selectClassCount();
        List<ZzClassQuantization> listAll = zzClassQuantizationDao.selectAllInfo();

        Map<Integer, Double> map1 = new LinkedHashMap<>();
        //完成行为量化分的合成
        for (int i = 0; i < count; i++ ){
            ZzClassQuantization list = listAll.get(i);
            double classSum = (list.getClassDiscipline() + list.getClassGrilDiscipline() +
                list.getClassManDiscipline()) / 3 * 0.4 + (list.getClassHealth() +
                list.getClassOcsd() + list.getClassGrilHealth() +
                list.getClassManHealth()) / 4 * 0.25 + Double.parseDouble(list.getClassGrasp()) * 0.2 +
                Double.parseDouble(list.getClassSpirit()) * 0.2 + list.getClassTeam() * 0.1;
            BigDecimal t = new BigDecimal(classSum);
            double classSums = t.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            listAll.get(i).setClassQuantization(classSums);

            map1.put(list.getId(), classSums);
        }


        //数据进行排序
        List<Map.Entry<Integer,Double>> list = new LinkedList<>(map1.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {

                return -((Comparable)((Map.Entry)(o1)).getValue()).compareTo(((Map.Entry)(o2)).getValue());
            }
        });
        map1.clear();
        for (Map.Entry<Integer, Double> entry : list) {
            map1.put(entry.getKey(), entry.getValue());
        }
        //根据得分算出名次
        Double a = -1.0;
        int i = 0, d = 0;
        for(Map.Entry<Integer,Double> entry:map1.entrySet()){
            if(a.equals(entry.getValue())) {
                i++;
                listAll.get(entry.getKey() - 1).setClassRanking(d);
            } else {
                a = entry.getValue(); d = ++i;
                listAll.get(entry.getKey() - 1).setClassRanking(d);
            }
        }
        //对listAll数据根据排名进行排序(从小到大)
        listAll = listAll.stream().sorted(Comparator.comparing(ZzClassQuantization::getClassRanking)).collect(Collectors.toList());
        //对listAll数据根据排名进行排序(从大到小)
        //listAll = listAll.stream().sorted(Comparator.comparing(ZzClassQuantization::getClassRanking).reversed()).collect(Collectors.toList());

        return listAll;
    }

    /**
     * 添加量化
     * @param zzClassLhDetails 量化详情字段类
     * @return
     */
    @Override
    public Integer insertLhDetails(ZzClassLhDetails zzClassLhDetails) {
        if (zzClassLhDetails.getClassTypeKey() == null){
            zzClassLhDetails.setClassTypeKey(classLhTypeDao.selectByClassTypeValue(zzClassLhDetails.getClassType()));
        }
        System.out.println("========className=========" + zzClassLhDetails.getClassName());
        String name[], className = "", classNames = zzClassLhDetails.getClassName() + ',';
        try {
            for(int i = 0; i < classNames.length(); i++){
                if (classNames.charAt(i) != ','){
                    className += classNames.charAt(i);
                    System.out.println("========className11=========" + className);
                } else {
                    System.out.println("========className=========" + className);
                    String classTeacher = zzClassQuantizationDao.selectByClassTeacher(className);
                    String num = zzClassQuantizationDao.selectCertainInfo(className,
                            zzClassLhDetails.getClassTypeKey());
                    double points = Double.parseDouble(num) - zzClassLhDetails.getClassPoints();

                    zzClassQuantizationDao.updateCertainInfo( valueOf(points),
                            className,zzClassLhDetails.getClassTypeKey());
                    zzClassLhDetails.setClassName(className);
                    zzClassLhDetails.setClassTeacher(classTeacher);
                    zzClassLhDetailsDao.insertSelective( zzClassLhDetails );
                    className = "";
                }
            }
            return 1;
        } catch (Exception e) {
            System.out.println("量化添加失败了！！！" + e.getMessage());
            return 0;
        }
    }

    /**
     * @return 获得所有量化类型
     */
    @Override
    public List<ClassLhType> selectAllType() {
        return classLhTypeDao.selectAllType();
    }

    /**
     * @return 返回所有班级
     */
    @Override
    public List<ZzClassQuantization> selectAllClass() {
        return zzClassQuantizationDao.selectAllClass();
    }

    /**
     * 返回所选班级的量化明细
     * @param classType  量化类型字段
     * @param className  班级名称
     * @return 此班级的量化明细
     */
    @Override
    public List<ZzClassLhDetails> selectClassDetails(String classType, String className) {
        return zzClassLhDetailsDao.selectClassDetails(classType, className);
    }

    /**
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param className 班级名称
     * @param classType 量化类型
     * @return 所有符合条件的量化明细
     */
    @Override
    public List<ZzClassLhDetails> selectClassLhDetails(String startTime, String endTime, String className, String classType) {
        return zzClassLhDetailsDao.selectClassLhDetails(startTime, endTime, className, classType);
    }

    /**
     * 删除填写错误的量化详情
     * @param zzClassLhDetails 量化详情
     * @return
     */
    @Override
    public Integer deleteDetail(ZzClassLhDetails zzClassLhDetails) {
        String classTypeKey = classLhTypeDao.selectByClassTypeValue(zzClassLhDetails.getClassType());
        String num = zzClassQuantizationDao.selectCertainInfo(zzClassLhDetails.getClassName(),
                classTypeKey);

        double points = Double.parseDouble(num) + zzClassLhDetails.getClassPoints();
        zzClassQuantizationDao.updateCertainInfo( valueOf(points),
                zzClassLhDetails.getClassName(),classTypeKey);
        log.info("+++++++", zzClassLhDetails.getId());
        return zzClassLhDetailsDao.deleteByPrimaryKey(zzClassLhDetails.getId());
    }

    /**
     * 下载图片保存图片到本地
     * @param file
     * @param fileUser
     * @return 返回图片保存路径
     */
    @Override
    public String savePicByFormData(MultipartFile file, String fileUser) {
        try {
            // 图片存储路径
            String path = "D:\\Project\\school_manage\\src\\assets\\lhImage";

            // 判断是否有路径
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
            String fileName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
//            String fileName = fileUser + ".png";
            File tempFile = new File(path, fileName);
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }
            file.transferTo(tempFile);
            String imagePath = path + "\\" + fileName;
            System.out.println("+++++++++++path + fileName++++++++++++" + imagePath);
            return imagePath;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return exception.getMessage();
        }
    }

    /**
     * 班级量化分数合成
     */
    @Override
    public List<ZzClassCompose> selectAllComposeInfo(
            String className,Double classDiscipline, Double classGrilDiscipline, Double classManDiscipline,
            Double classHealth,Double classOcsd,Double classGrilHealth,Double classManHealth,String classGrasp,
            String classSpirit,Double classTeam, String classTeacher) {
        if (className != null){
            System.out.println("===============" + classGrasp);
            //合并班级量化数据
            ZzClassCompose zcz = zzClassComposeDao.selectByNameInfo(className, classTeacher);
            zcz.setClassDiscipline(Double.parseDouble(format("%2f", (zcz.getClassDiscipline() + classDiscipline) / 2)));
            zcz.setClassGrilDiscipline(Double.parseDouble(format("%2f", (zcz.getClassGrilDiscipline() + classGrilDiscipline) / 2)));
            zcz.setClassManDiscipline(Double.parseDouble(format("%2f", (zcz.getClassManDiscipline() + classManDiscipline) / 2)));
            zcz.setClassHealth(Double.parseDouble(format("%2f", (zcz.getClassHealth() + classHealth) / 2)));
            zcz.setClassOcsd(Double.parseDouble(format("%2f", (zcz.getClassOcsd() + classOcsd) / 2)));
            zcz.setClassGrilHealth(Double.parseDouble(format("%2f", (zcz.getClassGrilHealth() + classGrilHealth) / 2)));
            zcz.setClassManHealth(Double.parseDouble(format("%2f", (zcz.getClassManHealth() + classManHealth) / 2)));
            String classGrasp1 = format("%.2f", (Double.parseDouble(zcz.getClassGrasp()) + Double.parseDouble(classGrasp)) / 2);
            zcz.setClassGrasp(classGrasp1);
            String classSpirit1 = format("%.2f", (Double.parseDouble(zcz.getClassSpirit()) + Double.parseDouble(classSpirit)) / 2);
            zcz.setClassSpirit(classSpirit1);
            zcz.setClassTeam(Double.parseDouble(format("%2f", (zcz.getClassTeam() + classTeam) / 2)));
            zzClassComposeDao.updateClassCompose( zcz.getClassDiscipline(),  zcz.getClassGrilDiscipline(),
                    zcz.getClassManDiscipline(), zcz.getClassHealth(),zcz.getClassOcsd(), zcz.getClassGrilHealth(),
                    zcz.getClassManHealth(), zcz.getClassGrasp(),zcz.getClassSpirit(), zcz.getClassTeam(), zcz.getClassName());
            //对合并后的数据排名、排序
            List<ZzClassCompose> listAll = zzClassComposeDao.selectAllInfo();
            System.out.println("========排序后list======" + listAll);
            Map<Integer, Double> map1 = new LinkedHashMap<>();
            int count = zzClassQuantizationDao.selectClassCount();
            //完成行为量化分的合成
            for (int i = 0; i < count; i++ ){
                ZzClassCompose list = listAll.get(i);
                double classSum = (list.getClassDiscipline() + list.getClassGrilDiscipline() +
                        list.getClassManDiscipline()) / 3 * 0.4 + (list.getClassHealth() +
                        list.getClassOcsd() + list.getClassGrilHealth() +
                        list.getClassManHealth()) / 4 * 0.25 + Double.parseDouble(list.getClassGrasp()) * 0.2 +
                        Double.parseDouble(list.getClassSpirit()) * 0.2 + list.getClassTeam() * 0.1;
                BigDecimal t = new BigDecimal(classSum);
                double classSums = t.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                listAll.get(i).setClassQuantization(classSums);

                map1.put(list.getId(), classSums);
            }
            System.out.println("====整合后的map1===="+map1);

            //数据进行排序
            List<Map.Entry<Integer,Double>> list = new LinkedList<>(map1.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
                @Override
                public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {

                    return -((Comparable)((Map.Entry)(o1)).getValue()).compareTo(((Map.Entry)(o2)).getValue());
                }
            });
            map1.clear();
            for (Map.Entry<Integer, Double> entry : list) {
                map1.put(entry.getKey(), entry.getValue());
            }
            //根据得分算出名次
            Double a = -1.0;
            int i = 0, d = 0;
            for(Map.Entry<Integer,Double> entry:map1.entrySet()){
                if(a.equals(entry.getValue())) {
                    i++;
                    listAll.get(entry.getKey() - 1).setClassRanking(d);
                } else {
                    a = entry.getValue(); d = ++i;
                    listAll.get(entry.getKey() - 1).setClassRanking(d);
                }
            }
            //对listAll数据根据排名进行排序(从小到大)
            listAll = listAll.stream().sorted(Comparator.comparing(ZzClassCompose::getClassRanking)).collect(Collectors.toList());
            //对listAll数据根据排名进行排序(从大到小)
            //listAll = listAll.stream().sorted(Comparator.comparing(ZzClassQuantization::getClassRanking).reversed()).collect(Collectors.toList());

            return listAll;
        } else {
            //对合并后的数据排名、排序
            List<ZzClassCompose> listAll = zzClassComposeDao.selectAllInfo();

            Map<Integer, Double> map1 = new LinkedHashMap<>();
            int count = zzClassQuantizationDao.selectClassCount();
            //完成行为量化分的合成
            for (int i = 0; i < count; i++ ){
                ZzClassCompose list = listAll.get(i);
                double classSum = (list.getClassDiscipline() + list.getClassGrilDiscipline() +
                        list.getClassManDiscipline()) / 3 * 0.4 + (list.getClassHealth() +
                        list.getClassOcsd() + list.getClassGrilHealth() +
                        list.getClassManHealth()) / 4 * 0.25 + Double.parseDouble(list.getClassGrasp()) * 0.2 +
                        Double.parseDouble(list.getClassSpirit()) * 0.2 + list.getClassTeam() * 0.1;
                BigDecimal t = new BigDecimal(classSum);
                double classSums = t.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                listAll.get(i).setClassQuantization(classSums);

                map1.put(list.getId(), classSums);
            }


            //数据进行排序
            List<Map.Entry<Integer,Double>> list = new LinkedList<>(map1.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
                @Override
                public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {

                    return -((Comparable)((Map.Entry)(o1)).getValue()).compareTo(((Map.Entry)(o2)).getValue());
                }
            });
            map1.clear();
            for (Map.Entry<Integer, Double> entry : list) {
                map1.put(entry.getKey(), entry.getValue());
            }
            //根据得分算出名次
            Double a = -1.0;
            int i = 0, d = 0;
            for(Map.Entry<Integer,Double> entry:map1.entrySet()){
                if(a.equals(entry.getValue())) {
                    i++;
                    listAll.get(entry.getKey() - 1).setClassRanking(d);
                } else {
                    a = entry.getValue(); d = ++i;
                    listAll.get(entry.getKey() - 1).setClassRanking(d);
                }
            }
            //对listAll数据根据排名进行排序(从小到大)
            listAll = listAll.stream().sorted(Comparator.comparing(ZzClassCompose::getClassRanking)).collect(Collectors.toList());
            //对listAll数据根据排名进行排序(从大到小)
            //listAll = listAll.stream().sorted(Comparator.comparing(ZzClassQuantization::getClassRanking).reversed()).collect(Collectors.toList());

            return listAll;
        }
    }

}
