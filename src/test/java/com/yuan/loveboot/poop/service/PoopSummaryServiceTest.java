package com.yuan.loveboot.poop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PoopSummaryServiceTest {
    private final PoopSummaryService service;

    @Autowired
    public PoopSummaryServiceTest(PoopSummaryService service) {
        this.service = service;
    }

    @Test
    void syncLastMonthPoopStatisticsScheduled() {
        service.syncLastMonthPoopStatisticsScheduled();
    }
}