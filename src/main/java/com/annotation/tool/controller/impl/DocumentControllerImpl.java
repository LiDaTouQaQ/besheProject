package com.annotation.tool.controller.impl;

import com.annotation.tool.controller.DocumentController;
import com.annotation.tool.entity.Result;
import com.annotation.tool.entity.enums.ResultEnum;
import com.annotation.tool.service.ProjectContentService;
import com.annotation.tool.util.ResultGeneratorUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName DocumentControllerImpl
 * @Author Liyh
 * @Date 2024.04.10 13:40
 * @Description:
 **/
@Controller
public class DocumentControllerImpl implements DocumentController {

    private final static String FILE_UPLOAD_PATH = "D:/upload/";
    @Autowired
    ProjectContentService projectContentService;

    @Override
    @RequestMapping(value = "/uploadContentFile",method = RequestMethod.POST)
    @ResponseBody
    public Result ProjectContentDocumentFile(@RequestParam("file") MultipartFile file,@RequestParam("projectId") String projectId) {
        if(file.isEmpty()){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"文件上传为空");
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件通用名称
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        try {
            // 读取文件内容
            InputStream inputStream = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // todo 处理文件内容
            int max = projectContentService.maxContentID(projectId)+1;
            return projectContentService.executeFileContent(reader,projectId, max);

        }catch (IOException e){
            e.printStackTrace();
        }
        return ResultGeneratorUtil.genSuccessResult();
    }

    @Override
    public Result EntityDocumentFile(MultipartFile file) {
        return null;
    }

    @Override
    public Result RelationDocumentFile(MultipartFile file) {
        return null;
    }
}
