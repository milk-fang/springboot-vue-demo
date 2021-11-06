package com.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CommentAndUser {

    /*
    *  select content,create_time,user_id, username, img
         from comment
         left join user
         on comment.user_id = user.id
         where comment.article_id = #{articleId};
    * */
    private Integer id;
    private String content;

    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private String img;

    private Integer userId;

    private Integer likes;

    // comments总数
    private Integer total;


}
