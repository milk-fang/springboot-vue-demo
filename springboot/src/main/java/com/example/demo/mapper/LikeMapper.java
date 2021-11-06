package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Like;

import java.util.List;

public interface LikeMapper extends BaseMapper<Like> {
    List<Like> findByUidAndAid(Integer userId, Integer commentId);
    List<Like> findByUid(Integer userId);
    List<Like> findByAid(Integer commentId);
    void insertByUidAndAid(Integer userId, Integer commentId);
    void deleteByUidAndAid(Integer userId, Integer commentId);
}
