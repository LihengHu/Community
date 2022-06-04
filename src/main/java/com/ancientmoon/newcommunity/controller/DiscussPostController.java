package com.ancientmoon.newcommunity.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ancientmoon.newcommunity.dao.mapper.UserMapper;
import com.ancientmoon.newcommunity.entity.DiscussPost;
import com.ancientmoon.newcommunity.entity.User;
import com.ancientmoon.newcommunity.service.DiscussPostService;
import com.ancientmoon.newcommunity.service.UserService;
import com.ancientmoon.newcommunity.utils.CommunityUtil;
import com.ancientmoon.newcommunity.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;


    //异步
    //客户端不需要等待服务器端的响应。在服务器处理请求的过程中，客户端可以进行其他的操作。
    @PostMapping("/add")
    @ResponseBody
    public String addDiscussPost(String title,String content){
        User user = hostHolder.getUser();
        if(user == null){
            return CommunityUtil.getJSONString(403,"你还没有登录！");
            //JSON只是一种格式
        }
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);
        //报错的情况统一处理
        return CommunityUtil.getJSONString(0,"发送成功");
    }

    @GetMapping("/detail/{discussPostId}")
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId , Model model){
        DiscussPost discussPost = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post",discussPost);
        User user = userService.findUserById(discussPost.getUserId());
        model.addAttribute("user",user);
        return "/site/discuss-detail";
    }

}
