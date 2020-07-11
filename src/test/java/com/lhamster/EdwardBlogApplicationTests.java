package com.lhamster;

import com.lhamster.util.SmsUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EdwardBlogApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(SmsUtils.generateRandomCode());
    }

}
