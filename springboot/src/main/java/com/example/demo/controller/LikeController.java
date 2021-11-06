package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Like;
import com.example.demo.entity.User;
import com.example.demo.mapper.LikeMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.example.demo.common.Result.success;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Resource
    private LikeMapper likeMapper;

    @RequestMapping("/great")
    public Result<?> great(@Param("userId") Integer userId, @Param("commentId") Integer commentId){
        List<Like> list = likeMapper.findByUidAndAid(userId,commentId);
        return Result.success(list);
    }

    @RequestMapping("/great/user")
    public Result<?> great2(@Param("userId") Integer userId){
        List<Like> list = likeMapper.findByUid(userId);
        return Result.success(list);
    }

    @RequestMapping("/great/comment")
    public Result<?> great3( @Param("commentId") Integer commentId){
        List<Like> list = likeMapper.findByAid(commentId);
        return Result.success(list);
    }


    @PostMapping
    public Result<?> save(@RequestBody Like like){
       likeMapper.insertByUidAndAid(like.getUserId(), like.getCommentId());
       return Result.success();
    }

    @DeleteMapping()
    public Result<?> update(@Param("userId") Integer userId, @Param("commentId") Integer commentId){
        likeMapper.deleteByUidAndAid(userId,commentId);
        return Result.success();
    }
}
