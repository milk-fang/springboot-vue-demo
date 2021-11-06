package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Article;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.mapper.ArticleMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    ArticleMapper articleMapper;

    @PostMapping
    public Result<?> save(@RequestBody Article article){
        article.setCreateTime(new Date());
        articleMapper.insert(article);
        return Result.success();

    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(articleMapper.selectById(id));
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){

        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Article::getContent, search);
        }
        Page<Article> userPage = articleMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        return Result.success(userPage);
    }
}
