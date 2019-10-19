package com.ydyno;

import com.ydyno.service.GeneratorService;
import com.ydyno.util.GeneratorUtil;
import com.ydyno.vo.ColumnInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class GeneratorDomeApplicationTests {

    @Autowired
    private GeneratorService generatorService;

    @Test
    void genTest() throws IOException {
        String tableName = "job";
        String pack = "com.ydyno";
        String author = "Zheng Jie";
        List<ColumnInfo> columnInfos = generatorService.getColumns(tableName);
        GeneratorUtil.generatorCode(columnInfos,pack,author,tableName);
    }

}
