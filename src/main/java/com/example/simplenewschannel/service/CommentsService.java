package com.example.simplenewschannel.service;

import com.example.simplenewschannel.entity.Comment;

import java.util.List;

public interface CommentsService {
    List<Comment> findAll();
    Comment findById(long id);
}
