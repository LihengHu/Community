package com.ancientmoon.newcommunity;

import com.ancientmoon.newcommunity.dao.mapper.DiscussPostMapper;
import com.ancientmoon.newcommunity.entity.DiscussPost;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class MapperTest {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    @DisplayName("测试post")
    void testselectPosts()
    {
        int n = discussPostMapper.selectDiscussPostRows(0);
        Assertions.assertEquals(n,149);
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0,0,10);
        for (DiscussPost discussPost:
             list) {
            System.out.println(discussPost);
        }
    }
}
