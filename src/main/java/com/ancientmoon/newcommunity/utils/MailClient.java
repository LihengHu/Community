package com.ancientmoon.newcommunity.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}") //从配置文件中取的
    private String from;

    public void sendMail(String to ,String subject ,String content){

        try {
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            mailSender.send(helper.getMimeMessage());

        } catch (MessagingException e) {
            logger.error("发送邮件失败："+e.getMessage());
        }

    }

}
