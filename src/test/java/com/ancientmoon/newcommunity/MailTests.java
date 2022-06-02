package com.ancientmoon.newcommunity;

import com.ancientmoon.newcommunity.utils.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;

@SpringBootTest
public class MailTests {
    @Autowired
    private MailClient mailClient;

    @Resource
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        String to = "936933606@qq.com";
        mailClient.sendMail(to,"hlhnb","胡立恒用java发送的邮件");
    }

    @Test
    public void testHtmlMail(){
        String to = "936933606@qq.com";
        String SLL= "2251437787@qq.com";
        String ZWJ= "810886242@qq.com";
        Context context = new Context();
        context.setVariable("username",ZWJ);
        String process = templateEngine.process("/mail/demo", context);
        mailClient.sendMail(ZWJ,"TEST",process);
    }
}
