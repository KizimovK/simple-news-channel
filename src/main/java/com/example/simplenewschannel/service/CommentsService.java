package com.example.simplenewschannel.service;

import com.example.simplenewschannel.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentsService {
    List<Comment> findAll();
    Comment findById(long id);

    Page<Comment> findAllByNewsId(long newsId, Pageable pageable);

    Comment save(Comment comment, long newsId, long userId);

    Comment update(Comment comment, long id);



    void deleteById(long id);
}
