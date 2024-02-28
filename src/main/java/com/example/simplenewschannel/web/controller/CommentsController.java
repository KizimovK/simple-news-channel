package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.aop.Accessible;
import com.example.simplenewschannel.dto.request.PaginationRequest;
import com.example.simplenewschannel.dto.request.UpsertCommentRequest;
import com.example.simplenewschannel.dto.response.CommentResponse;
import com.example.simplenewschannel.dto.response.ModelListResponse;
import com.example.simplenewschannel.entity.Comment;
import com.example.simplenewschannel.mapper.CommentsMapper;
import com.example.simplenewschannel.service.CommentsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ModelListResponse<CommentResponse>> getCommentsByNews(@Valid PaginationRequest request,
                                                                                @RequestParam long newsId){
        Page<Comment> commentPage = commentsService.findAllByNewsId(newsId, request.pageRequest());
        return ResponseEntity.ok( ModelListResponse.<CommentResponse>builder()
                .totalCount(commentPage.getTotalElements())
                .data(commentPage.stream().map(commentsMapper::commentToResponse).toList())
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable long id){
        return ResponseEntity.ok(commentsMapper.commentToResponse(commentsService.findById(id)));
    }
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody UpsertCommentRequest request){
        Comment newComment = commentsService.save(commentsMapper.requestToComment(request),
                request.getNewsId(),request.getUserId());
        return ResponseEntity.ok(commentsMapper.commentToResponse(newComment));
    }
    @Accessible(checkBy = AccessType.COMMENT)
    @PutMapping("/{id}")
    ResponseEntity<CommentResponse> updateComment(@PathVariable long id, @RequestBody UpsertCommentRequest request){
        Comment updateComment = commentsService.update(commentsMapper.requestToComment(request), id);
        return ResponseEntity.ok(commentsMapper.commentToResponse(updateComment));
    }
    @Accessible(checkBy = AccessType.COMMENT)
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteComment(@PathVariable long id, long userId){
        commentsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
