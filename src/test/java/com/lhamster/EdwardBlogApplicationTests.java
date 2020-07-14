package com.lhamster;

import com.lhamster.util.TencentCOSUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

@SpringBootTest
class EdwardBlogApplicationTests {
    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() {
        File file = new File("C:\\Users\\Edward\\Desktop\\logo.jpg");
        System.out.println(TencentCOSUtil.uploadObject(file, "headPicture/" + file.getName()));
    }

}
