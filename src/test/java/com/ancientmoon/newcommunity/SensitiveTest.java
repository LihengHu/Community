package com.ancientmoon.newcommunity;

import com.ancientmoon.newcommunity.utils.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SensitiveTest {

    @Autowired
    SensitiveFilter sensitiveFilter;

    @Test
    public void testFilter(){
        System.out.println(sensitiveFilter.filter("我要🌟吸🌟毒🌟和嫖娼，他要赌博"));
    }
}
