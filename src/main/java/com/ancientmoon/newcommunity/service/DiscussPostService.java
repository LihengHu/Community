package com.ancientmoon.newcommunity.service;

import com.ancientmoon.newcommunity.dao.mapper.DiscussPostMapper;
import com.ancientmoon.newcommunity.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {

    @Autowired
    DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDisscussPosts(int usrId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(usrId, offset, limit);
    }

    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

}
