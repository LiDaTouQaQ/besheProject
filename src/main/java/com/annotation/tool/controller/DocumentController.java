package com.annotation.tool.controller;

import com.annotation.tool.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @ClassName DocumentController
 * @Author Liyh
 * @Date 2024.04.10 13:39
 * @Description:
 **/
public interface DocumentController {
    Result ProjectContentDocumentFile(MultipartFile file, String projectId);

    Result EntityDocumentFile(MultipartFile file);

    Result RelationDocumentFile(MultipartFile file);
}
