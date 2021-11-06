package com.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CommentAndReportedUser {
    private Integer id;

    // 举报的评论内容
    private String content;

    // 举报者用户名
    private String username;

    // 被举报者用户名
    private String reportedUsername;


    // 举报时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    // 举报理由
    private String reason;

    // 被举报的评论的id
    private Integer commentId;

}
