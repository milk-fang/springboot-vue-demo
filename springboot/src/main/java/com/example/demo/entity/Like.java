package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("like")
@Data
public class Like {
    @TableId(type = IdType.AUTO)
    private Integer id;

    // 点赞对应的用户
    private Integer userId;

    // 点赞对应的comment
    private Integer commentId;
}
