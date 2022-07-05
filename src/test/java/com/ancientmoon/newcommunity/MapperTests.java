package com.ancientmoon.newcommunity;

import com.ancientmoon.newcommunity.dao.mapper.DiscussPostMapper;
import com.ancientmoon.newcommunity.dao.mapper.LoginTicketMapper;
import com.ancientmoon.newcommunity.dao.mapper.MessageMapper;
import com.ancientmoon.newcommunity.entity.DiscussPost;
import com.ancientmoon.newcommunity.entity.LoginTicket;
import com.ancientmoon.newcommunity.entity.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;


@SpringBootTest
public class MapperTests {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Test
    @DisplayName("测试post")
    void testselectPosts()
    {
        int n = discussPostMapper.selectDiscussPostRows(0);
        Assertions.assertEquals(n,149);
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0,0,10,0);
        for (DiscussPost discussPost:
             list) {
            System.out.println(discussPost);
        }
    }

    @Test
    public void testInsertLoginTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));

        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectLoginTicket() {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);
        loginTicketMapper.updateStatus("abc", 1);
        loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);
    }

    @Test
    public void testSelectLetters() {
        List<Message> list = messageMapper.selectConversations(111, 0, 20);
        for (Message message : list) {
            System.out.println(message);
        }
        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);

        list = messageMapper.selectLetters("111_112", 0, 10);
        for (Message message : list) {
            System.out.println(message);
        }

        count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);

        count = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(count);
    }
}
