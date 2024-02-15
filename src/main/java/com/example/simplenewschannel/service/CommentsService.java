package com.example.simplenewschannel.service;

import com.example.simplenewschannel.entity.CommentNews;

import java.util.List;

public interface CommentsService {
    List<CommentNews> findAll();
    CommentNews findById(long id);
}
