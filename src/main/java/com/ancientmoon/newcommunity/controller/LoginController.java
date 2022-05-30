package com.ancientmoon.newcommunity.controller;

import com.ancientmoon.newcommunity.entity.User;
import com.ancientmoon.newcommunity.service.UserService;
import com.google.code.kaptcha.Producer;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private Producer producer;

    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/register")
    public String getRegisterPage(){
        //跳转到注册页面
        return "/site/register";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/login";
    }

    @PostMapping("/register")
    public String register(Model model, User user) {
        Map<String,Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            model.addAttribute("msg", "注册成功,我们已经向您的邮箱发送了一封激活邮件,请尽快激活!");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }

    @GetMapping("/activation/{userId}/{code}")
    public String activation(Model model,@PathVariable("userId") int userId , @PathVariable("code") String code){
        int result = userService.activation(userId, code);
        if (result == userService.ACTIVATION_SUCCESS) {
            model.addAttribute("msg", "激活成功,您的账号已经可以正常使用了!");
            model.addAttribute("target", "/login");
        } else if (result == userService.ACTIVATION_REPEAT) {
            model.addAttribute("msg", "无效操作,该账号已经激活过了!");
            model.addAttribute("target", "/index");
        } else {
            model.addAttribute("msg", "激活失败,您提供的激活码不正确!");
            model.addAttribute("target", "/index");
        }
        return "/site/operate-result";
    }

    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse httpServletResponse, HttpSession session){
        //生成验证码
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);
        //将验证码存入session
        session.setAttribute("kaptcha",text);
        httpServletResponse.setContentType("image/png");
        try {
            OutputStream os = httpServletResponse.getOutputStream();
            ImageIO.write(image,"png",os);
        } catch (IOException e) {
            logger.error("响应验证码失败"+e.getMessage());
        }
    }
}
