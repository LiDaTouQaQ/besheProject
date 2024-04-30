package com.annotation.tool.service;

import com.annotation.tool.entity.ContentRelationEntity;
import com.annotation.tool.entity.Result;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName DocumentExportService
 * @Author Liyh
 * @Date 2024.04.22 09:24
 * @Description:
 **/
public interface DocumentExportService {

    Result exportRelationAndEntity(String projectId);
    void exportRelationAndEntity(HttpServletResponse response,String projectId, List<String> contentId);
    Result exportRelationNeo4j(String projectId, List<String> contentId) throws Exception;

    void exportRelationJson(HttpServletResponse response, String projectId,List<String> contentId) throws IOException;
}
