package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.mapper.CourseMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseMapper courseMapper;

    @GetMapping
    public Result<?> getAllCourse(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Course> wrapper = Wrappers.<Course>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Course::getName, search);
        }
        Page<Course> coursePage = courseMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        return Result.success(coursePage);
    }

    @GetMapping("/all")
    public Result<?> getAllCourse(){
        List<Course> courses = courseMapper.selectList(null);
        return Result.success(courses);
    }


    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        courseMapper.deleteById(id);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Course course){
        courseMapper.updateById(course);
        return Result.success();
    }

    @PostMapping
    public Result<?> save(@RequestBody Course course){
        courseMapper.insert(course);
        return Result.success();
    }
}
