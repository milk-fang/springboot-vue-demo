package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Report;
import com.example.demo.vo.CommentAndReportedUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper extends BaseMapper<Report> {
    List<CommentAndReportedUser> getAllData();
}
