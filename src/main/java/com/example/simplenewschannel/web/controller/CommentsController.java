package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.aop.Accessible;
import com.example.simplenewschannel.dto.request.PaginationRequest;
import com.example.simplenewschannel.dto.request.UpsertCommentRequest;
import com.example.simplenewschannel.dto.response.CommentResponse;
import com.example.simplenewschannel.dto.response.ModelListResponse;
import com.example.simplenewschannel.entity.Comment;
import com.example.simplenewschannel.mapper.CommentsMapper;
import com.example.simplenewschannel.security.AppUserDetails;
import com.example.simplenewschannel.service.CommentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsMapper commentsMapper;
    private final CommentsService commentsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    public ModelListResponse<CommentResponse> getCommentsByNews(@Valid PaginationRequest request,
                                                                @RequestParam long newsId) {
        Page<Comment> commentPage = commentsService.findAllByNewsId(newsId, request.pageRequest());
        return ModelListResponse.<CommentResponse>builder()
                .totalCount(commentPage.getTotalElements())
                .data(commentPage.stream().map(commentsMapper::commentToResponse).toList())
                .build();
    }

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    public CommentResponse getComment(@PathVariable long commentId) {
        return commentsMapper.commentToResponse(commentsService.findById(commentId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    public CommentResponse createComment(@RequestBody UpsertCommentRequest request,
                                         @AuthenticationPrincipal AppUserDetails userDetails) {
        Comment newComment = commentsService.save(commentsMapper.requestToComment(request),
                request.getNewsId(), userDetails.getId());
        return commentsMapper.commentToResponse(newComment);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    @Accessible(checkBy = AccessType.COMMENT)
    public CommentResponse updateComment(@PathVariable long commentId,
                                         @RequestBody UpsertCommentRequest request) {
        Comment updateComment = commentsService.update(commentsMapper.requestToComment(request), commentId);
        return commentsMapper.commentToResponse(updateComment);
    }



    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    @Accessible(checkBy = AccessType.COMMENT, availableForModerator = true)
    public void deleteComment(@PathVariable long commentId) {
        commentsService.deleteById(commentId);
    }
}
