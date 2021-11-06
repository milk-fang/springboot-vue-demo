package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("course")
@Data
public class Course {

    @TableId(type = IdType.AUTO)
    private Integer id;
    // 课程名称
    private String name;

    // 授课教师
    private String teacher;

    //课程类型 公共基础 专业课 通识
    private String type;
}
