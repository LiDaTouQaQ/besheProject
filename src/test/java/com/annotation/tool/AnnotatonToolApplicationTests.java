package com.annotation.tool;

import com.annotation.tool.service.DocumentExportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AnnotatonToolApplicationTests {
    @Autowired
    DocumentExportService exportService;

    @Test
    void contextLoads() {
        List<String> ids = new ArrayList<>();
        ids.add("10000");
        ids.add("10001");
        // exportService.exportRelationJson("60bee9add8cd4cdbac078f769fcf578f",ids);
    }

}
