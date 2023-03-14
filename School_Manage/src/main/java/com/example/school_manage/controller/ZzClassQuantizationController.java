package com.example.school_manage.controller;

import com.example.school_manage.model.ClassLhType;
import com.example.school_manage.model.ZzClassCompose;
import com.example.school_manage.model.ZzClassLhDetails;
import com.example.school_manage.model.ZzClassQuantization;
import com.example.school_manage.service.ZzClassQuantizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author macro
 * {@code @date} 2022/12/15
 */
@Api(tags = "ZzClassQuantizationController", description = "班级量化")
@RestController
@RequestMapping("/classInfo")
@Log4j2
public class ZzClassQuantizationController {
    @Autowired
    private ZzClassQuantizationService zzClassQuantizationService;

    @ApiOperation("获得各班级量化数据")
    @GetMapping
    public Map<String, Object> selectAllInfo(){
        Map<String, Object> map = new HashMap<>();
        List<ZzClassQuantization> list = zzClassQuantizationService.selectAllInfo();
        map.put("data",list);
        return map;
    }

    @ApiOperation("添加量化详细数据")
    @PostMapping
    //@CrossOrigin(origins = {"http://localhost:8081", "http://localhost:8080"})
    public Integer insertLhDetails(  @RequestBody ZzClassLhDetails zzClassLhDetails){
        System.out.println("=====getClassName====" + zzClassLhDetails.getClassName());
        System.out.println("=========" + zzClassLhDetails.getClassPoints());
        System.out.println("=========" + zzClassLhDetails.getClassDetail());
        System.out.println("=========" + zzClassLhDetails.getClassType());
        System.out.println("=========" + zzClassLhDetails.getClassTeacher());
        System.out.println("=====data====" + zzClassLhDetails.getClassDate());

        if(zzClassLhDetails.getClassImagePath() == "") {
            zzClassLhDetails.setClassImagePath(null);
        }
        System.out.println("=========" + zzClassLhDetails.getClassImagePath());
        return zzClassQuantizationService.insertLhDetails(zzClassLhDetails);
    }

    @ApiOperation("保存量化违纪图片")
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ResponseBody
    public String saveImage(@RequestParam("file") MultipartFile file, String fileUser) throws Exception {
        try {
            //文件保存名字
            String fileName = zzClassQuantizationService.savePicByFormData(file,fileUser);
            System.out.println("===============成功了=============");
            return fileName;
        } catch (Exception exception) {
            System.out.println("==========失败了===========");
           // return ResponseMsg.INSERT_UPDATE_ERROR;
            return exception.getLocalizedMessage();
        }

    }


    @ApiOperation("返回所有量化类型和班级")
    @GetMapping("/allItem")
    public Map<String, Object> selectAllItem(){
        Map<String, Object> map = new HashMap<>();
        List<ZzClassQuantization> quantizationList = zzClassQuantizationService.selectAllClass();
        List<ClassLhType> typeList = zzClassQuantizationService.selectAllType();
        map.put("classList", quantizationList);
        map.put("typeList", typeList);
        return map;
    }

//    /**
//     * 获得每个班某量化类型的详细数据
//     * @param classTypeValue 量化类型
//     * @param className 班级
//     * @return
//     */
//    @GetMapping("/details")
//    public Map<String, Object> selectClassDetails(@RequestParam String classTypeValue,
//                                                  @RequestParam String className){
//        Map<String, Object> map = new HashMap<>();
//        System.out.println("+++++" + className + "====" + classTypeValue);
//        List<ZzClassLhDetails> details = zzClassQuantizationService.selectClassDetails(classTypeValue, className);
//        map.put("details", details);
//        return map;
//    }
    @ApiOperation("根据条件查询量化明细")
    @GetMapping("/showDetails")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Map<String, Object> selectClassLhDetails(@RequestParam String startTime,
                                                    @RequestParam String endTime,
                                                    @RequestParam String className,
                                                    @RequestParam String classType){
        Map<String, Object> map = new HashMap<>();
        List<ZzClassLhDetails> list = zzClassQuantizationService.selectClassLhDetails(startTime, endTime, className, classType);
        for (ZzClassLhDetails zzClassLhDetails : list) {
            zzClassLhDetails.setClassDated(new SimpleDateFormat("yyyy-MM-dd").format(zzClassLhDetails.getClassDate()));
            String str = zzClassLhDetails.getClassImagePath();
            if (str != null) {
                String[]  strs=str.split("\\\\");
                String imagePath = "";
                for(int i = 0,len=strs.length;i<len;i++){
                    if (i == 6) {
                        imagePath = strs[i].toString();
                    }
                }
                System.out.println(imagePath);
                zzClassLhDetails.setClassImagePath(imagePath);
            }
        }
        map.put("data", list);
        return map;
    }

    @ApiOperation("删除错误的详细量化并且更改分数")
    @DeleteMapping("/delete")
    public Integer deleteDetail(@RequestBody ZzClassLhDetails zzClassLhDetails){
        System.out.println("++++++" + zzClassLhDetails.getId());
        return zzClassQuantizationService.deleteDetail(zzClassLhDetails);
    }

    @ApiOperation("合并班级量化分数")
    @PostMapping(value = "/compose")
    @ResponseBody
    public Map<String, Object> composeLhScore(@RequestBody ZzClassCompose zzClassCompose){
        System.out.println("=====getClassName====" + zzClassCompose.getClassName());
        System.out.println("=====getClassGrasp====" + zzClassCompose.getClassGrasp());
        System.out.println("=====getClassOcsd====" + zzClassCompose.getClassOcsd());
        System.out.println("=====getClassHealth====" + zzClassCompose.getClassHealth());
        System.out.println("=====getClassTeacher====" + zzClassCompose.getClassTeacher());
        List<ZzClassCompose> list = zzClassQuantizationService.selectAllComposeInfo(zzClassCompose.getClassName(),
                zzClassCompose.getClassDiscipline(), zzClassCompose.getClassGrilDiscipline(), zzClassCompose.getClassManDiscipline(),
                zzClassCompose.getClassHealth(), zzClassCompose.getClassOcsd(), zzClassCompose.getClassGrilHealth(),
                zzClassCompose.getClassManHealth(), zzClassCompose.getClassGrasp(), zzClassCompose.getClassSpirit(),
                zzClassCompose.getClassTeam(), zzClassCompose.getClassTeacher());

        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        return map;
    }

}
