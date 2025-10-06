package com.alexdev.week077;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class Week077ApplicationTests {

    @Test
    void contextLoads() {
    }

}
