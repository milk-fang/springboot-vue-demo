package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.entity.Report;
import com.example.demo.mapper.ReportMapper;
import com.example.demo.vo.CommentAndReportedUser;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Resource
    private ReportMapper reportMapper;

    @GetMapping
    public Result<?> getAllData(){
        List<CommentAndReportedUser> allData = reportMapper.getAllData();
        return Result.success(allData);
    }


    @PostMapping
    public Result<?> add(@RequestBody Report report){
        report.setCreateTime(new Date());
        reportMapper.insert(report);
        return Result.success();
    }


    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        reportMapper.deleteById(id);
        return Result.success();
    }
}
