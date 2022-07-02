package com.ancientmoon.newcommunity.controller;

import com.ancientmoon.newcommunity.entity.DiscussPost;
import com.ancientmoon.newcommunity.entity.Page;
import com.ancientmoon.newcommunity.entity.User;
import com.ancientmoon.newcommunity.service.DiscussPostService;
import com.ancientmoon.newcommunity.service.LikeService;
import com.ancientmoon.newcommunity.service.UserService;
import com.ancientmoon.newcommunity.utils.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class HomeController implements CommunityConstant {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    //返回的string是视图的名字
    @GetMapping("/index")
    public String getIndexPage(Model model,Page page){
        //page是客户端传过来的页码，已经在model中了，所以后面不需要再装入model
        // 方法调用栈,SpringMVC会自动实例化Model和Page,并将Page注入Model.
        // 所以,在thymeleaf中可以直接访问Page对象中的数据.
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");
        List<DiscussPost> lists = discussPostService.findDisscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (lists != null) {
            for (DiscussPost post : lists) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);//评论实体
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);//该评论的发起者的信息
                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST,post.getId());
                map.put("likeCount",likeCount);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "index";
    }

    @GetMapping("/error")
    public String getErrorPage(){
        return "/error/500";
    }

    @RequestMapping(path = "/denied", method = RequestMethod.GET)
    public String getDeniedPage() {
        return "/error/404";
    }

}
