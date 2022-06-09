package com.ancientmoon.newcommunity.dao.mapper;

import com.ancientmoon.newcommunity.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId , int offset ,int limit);

    //动态拼一个条件且有且只有一个条件，必须用Param来取一个别名
    //查discussPost一共有多少行
    int selectDiscussPostRows(@Param("userId") int userId);


    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    public int updateCommentCount(int id , int commentCount);
}
