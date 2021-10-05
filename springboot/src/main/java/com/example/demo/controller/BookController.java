package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    BookMapper bookMapper;

    @PostMapping
    public Result<?> save(@RequestBody Book book){

        bookMapper.insert(book);
        return Result.success();

    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){

        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Book::getName, search);
        }
        Page<Book> bookPage = bookMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        return Result.success(bookPage);
    }

    @PutMapping
    public Result<?> update(@RequestBody Book book){
        bookMapper.updateById(book);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        bookMapper.deleteById(id);
        return Result.success();
    }



    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(bookMapper.selectById(id));
    }
}
