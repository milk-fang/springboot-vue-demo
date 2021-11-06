package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Course;
import com.example.demo.entity.News;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.vo.CommentAndUser;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentMapper commentMapper;

    @GetMapping
    public Result<?> getCommentByArticleId(@RequestParam Integer articleId){

        List<CommentAndUser> comments = commentMapper.findCommentByArticleId(articleId);
        return Result.success(comments);

    }

    @PostMapping
    public Result<?> save(@RequestBody Comment comment){
        if("".equals(comment.getContent())){
            return Result.error("400","评论内容不能为空");
        }
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Comment comment){
        commentMapper.updateById(comment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        commentMapper.deleteById(id);
        return Result.success();
    }
}
