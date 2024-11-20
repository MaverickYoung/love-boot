package com.yuan.loveboot;

import com.yuan.loveboot.common.utils.YearMonthRange;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoveBootApplicationTests {

    @Test
    void contextLoads() {
        YearMonthRange range = new YearMonthRange();

        System.out.println(range);
        System.out.println(range.getStart());
        System.out.println(range.getStart().toString());
    }

}
