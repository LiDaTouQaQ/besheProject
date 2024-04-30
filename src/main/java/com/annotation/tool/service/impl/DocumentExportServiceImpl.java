package com.annotation.tool.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.annotation.tool.entity.ExportRelationEntity;
import com.annotation.tool.entity.Result;
import com.annotation.tool.mapper.ExportMapper;
import com.annotation.tool.service.DocumentExportService;
import com.annotation.tool.util.ExcelFillCellMergePrevColUtil;
import com.annotation.tool.util.ExcelFillCellMergeStrategyUtil;
import com.annotation.tool.util.ResultGeneratorUtil;
import com.annotation.tool.util.UUIDUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName DocumentExportServiceImpl
 * @Author Liyh
 * @Date 2024.04.22 09:26
 * @Description:
 **/
@Service
public class DocumentExportServiceImpl implements DocumentExportService {

    @Autowired
    ExportMapper exportMapper;

    @Override
    public Result exportRelationAndEntity(String projectId) {
        // 数据准备
        List<ExportRelationEntity> list = exportMapper.getExportDateByProjectId(projectId);
        String uuid = UUIDUtil.uuid();
        String path = writeExcel(list, uuid);
        // sendHttpPost(path, uuid);
        return ResultGeneratorUtil.genSuccessResult("导入成功");
    }

    @Override
    public void exportRelationAndEntity(HttpServletResponse response, String projectId, List<String> contentId) {
        // 数据准备
        List<ExportRelationEntity> list = exportMapper.getExportDateByProjectContentId(contentId, projectId);
        String uuid = UUIDUtil.uuid();
        try {
            // writeExcel(list, uuid);
            writeExcelDownload(response, list, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // sendHttpPost(path, uuid);
    }

    @Override
    public Result exportRelationNeo4j(String projectId, List<String> contentId) throws Exception {
        // 数据准备
        List<ExportRelationEntity> list = exportMapper.getExportDateByProjectContentId(contentId, projectId);
        String uuid = UUIDUtil.uuid();
        String path = writeExcel(list, uuid);
        sendHttpPost(path, uuid);
        return ResultGeneratorUtil.genSuccessResult("查看"+uuid.substring(0,5)+"集合");
    }

    @Override
    public void exportRelationJson(HttpServletResponse response, String projectId, List<String> contentId) throws IOException {
        // 数据准备
        List<ExportRelationEntity> list = exportMapper.getExportDateByProjectContentId(contentId, projectId);
        String uuid = UUIDUtil.uuid();
        writeJson(response,list,uuid);
    }

    public void writeExcelDownload(HttpServletResponse response, List<ExportRelationEntity> list, String UUID) throws IOException {
        String fileName = UUID + ".xlsx";
        // 处理导出数据
        for (ExportRelationEntity item : list) {
            item.setContent(item.getContentText() + "###实体");
            item.setEntityAndRelation(item.getEntityOne() + "###" + item.getRelationName());
            item.setEntity(item.getEntityTwo());
        }
        // 内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(new WriteCellStyle(), contentWriteCellStyle);
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.addHeader("Cache-Control", "no-cache");
        EasyExcel.write(response.getOutputStream(), ExportRelationEntity.class).autoCloseStream(Boolean.FALSE)
                .needHead(false)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(new ExcelFillCellMergeStrategyUtil(0, new int[]{0}, 100))
                .sheet("sheetOne")
                .doWrite(list);
    }

    // Excel文件创建
    public String writeExcel(List<ExportRelationEntity> list, String UUID) {
        String fileName = "E:/beshe/AnnotatonTool/" + UUID + ".xlsx";
        // 处理导出数据
        for (ExportRelationEntity item : list) {
            item.setContent(item.getContentText() + "###实体");
            item.setEntityAndRelation(item.getEntityOne() + "###" + item.getRelationName());
            item.setEntity(item.getEntityTwo());
        }
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("导出数据");
            int i = 0;
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            Map<String, List<ExportRelationEntity>> map = list.stream().collect(Collectors.groupingBy(ExportRelationEntity::getContent));
            for (String key : map.keySet()) {
                int length = map.get(key).size();
                int k = 0;
                List<ExportRelationEntity> groupList = map.get(key);
                for (ExportRelationEntity item : groupList) {
                    XSSFRow row = sheet.createRow(i);
                    if(k == 0){
                        XSSFCell cell = row.createCell(0);
                        cell.setCellValue(groupList.get(0).getContent());
                        k = 1;
                    }else{
                        XSSFCell cell = row.createCell(0);
                        cell.setCellValue("");
                    }
                    XSSFCell cell1 = row.createCell(1);
                    cell1.setCellValue(item.getEntityAndRelation());
                    XSSFCell cell2 = row.createCell(2);
                    cell2.setCellValue(item.getEntity());
                    i++;
                }
                if(length != 1){
                    CellRangeAddress region = new CellRangeAddress(i-length, i-1, 0, 0);
                    sheet.addMergedRegion(region);
                    // XSSFCell cell = sheet.getRow(i-1).getCell(0);
                    // cell.setCellStyle(style);
                }
            }
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public void writeJson(HttpServletResponse response, List<ExportRelationEntity> list, String UUID) throws IOException {
        String fileName = "E:/beshe/AnnotatonTool/" + UUID + ".json";
        // 数据分组
        Map<String, List<ExportRelationEntity>> map = list.stream().collect(Collectors.groupingBy(ExportRelationEntity::getContentText));
        File file = new File(fileName);
        try(FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);){
            for(String key : map.keySet()){
                pw.print("{");
                pw.print("\"text\":"+"\""+key+"\",");
                pw.print("\"spo_list\":[");
                List<ExportRelationEntity> entityList = map.get(key);
                int i = 0;
                for(ExportRelationEntity entity : entityList){
                    pw.print("{\"predicate\":\""+entity.getRelationName()+"\",");
                    pw.print("\"object_type\":{\"@value\":\""+entity.getEntityNameTwo()+"\"},");
                    pw.print("\"subject_type\":\""+entity.getEntityNameOne()+"\",");
                    pw.print("\"object\":{\"@value\":\""+entity.getEntityTwo()+"\"},");
                    pw.print("\"subject\":\""+entity.getEntityOne()+"\"");
                    pw.print("}");
                    i++;
                    if(i!=entityList.size()){
                        pw.print(",");
                    }
                }
                pw.print("],");
                pw.print("\"entities\":[");
                i = 0;
                for(ExportRelationEntity entity : entityList){
                    pw.print("{\"id\":"+entity.getEntityIdOne()+",");
                    pw.print("\"start_offset\":"+entity.getStartOne()+",");
                    pw.print("\"end_offset\":"+entity.getEndOne()+",");
                    pw.print("\"label\":\""+entity.getEntityNameOne()+"\"},");
                    pw.print("{\"id\":"+entity.getEntityIdTwo()+",");
                    pw.print("\"start_offset\":"+entity.getStartTwo()+",");
                    pw.print("\"end_offset\":"+entity.getEndTwo()+",");
                    pw.print("\"label\":\""+entity.getEntityNameTwo()+"\"}");
                    i++;
                    if(i!=entityList.size()){
                        pw.print(",");
                    }
                }
                pw.print("],");
                i = 0;
                pw.print("\"relations\":[");
                for(ExportRelationEntity entity : entityList){
                    pw.print("{\"id\":"+i+",");
                    pw.print("\"from_id\":"+entity.getEntityIdOne()+",");
                    pw.print("\"to_id\":"+entity.getEntityIdTwo()+",");
                    pw.print("\"type\":\""+entity.getRelationName()+"\"}");
                    i++;
                    if(i!=entityList.size()){
                        pw.print(",");
                    }
                }
                pw.print("]");
                pw.print("}\n");
                pw.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[(int)file.length()];
            fis.read(buffer);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.addHeader("Cache-Control", "no-cache");
            response.setContentType("application/octet-stream");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(buffer);
            outputStream.flush();
        }catch (Exception e){
            throw e;
        }

    }

    public void sendHttpPost(String filePath, String UUID) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        String url = "http://localhost/kg-api/importGraph";
        try {
            paramMap.put("domain", UUID.substring(0, 5));
            paramMap.put("type", "2");
            paramMap.put("file", FileUtil.file(filePath));
            String result = HttpUtil.post(url, paramMap);
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
