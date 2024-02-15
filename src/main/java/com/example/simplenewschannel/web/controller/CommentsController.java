package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.dto.response.CommentResponse;
import com.example.simplenewschannel.dto.response.CommentsListResponse;
import com.example.simplenewschannel.mapper.CommentsMapper;
import com.example.simplenewschannel.service.CommentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {
    private final CommentsMapper commentsMapper;
    private final CommentsService commentsService;

    public CommentsController(CommentsMapper commentsMapper, CommentsService commentsService) {
        this.commentsMapper = commentsMapper;
        this.commentsService = commentsService;
    }
    @GetMapping
    public ResponseEntity<CommentsListResponse> findAll(){
        return ResponseEntity.ok(commentsMapper.commentsListToResponseList(commentsService.findAll()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable long id){
        return ResponseEntity.ok(commentsMapper.commentToResponse(commentsService.findById(id)));
    }
}
