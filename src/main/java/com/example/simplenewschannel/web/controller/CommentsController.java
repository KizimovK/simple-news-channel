package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.aop.Accessible;
import com.example.simplenewschannel.dto.request.PaginationRequest;
import com.example.simplenewschannel.dto.request.UpsertCommentRequest;
import com.example.simplenewschannel.dto.response.CommentResponse;
import com.example.simplenewschannel.dto.response.ExceptionResponse;
import com.example.simplenewschannel.dto.response.ModelListResponse;
import com.example.simplenewschannel.entity.Comment;
import com.example.simplenewschannel.mapper.CommentsMapper;
import com.example.simplenewschannel.service.CommentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@Tag(name = "Comment", description = " Comment News API")
public class CommentsController {
    private final CommentsMapper commentsMapper;
    private final CommentsService commentsService;

    public CommentsController(CommentsMapper commentsMapper, CommentsService commentsService) {
        this.commentsMapper = commentsMapper;
        this.commentsService = commentsService;
    }
    @Operation(
            summary = "Get all comments by news id",
            description = "Get all comments by news id. Return id, id news, user name, create time and comment text"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200"
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            )
    })
    @GetMapping
    public ResponseEntity<ModelListResponse<CommentResponse>> getCommentsByNews(@Valid PaginationRequest request,
                                                                                @RequestParam long newsId){
        Page<Comment> commentPage = commentsService.findAllByNewsId(newsId, request.pageRequest());
        return ResponseEntity.ok(ModelListResponse.<CommentResponse>builder()
                .totalCount(commentPage.getTotalElements())
                .data(commentPage.stream().map(commentsMapper::commentToResponse).toList())
                .build());
    }
    @Operation(
            summary = "Get comment by id",
            description = "Get comment id. Return id, id news, user name, create time and comment text"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200"
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable long id){
        return ResponseEntity.ok(commentsMapper.commentToResponse(commentsService.findById(id)));
    }
    @Operation(
            summary = "Create comment",
            description = "Add entity comment. " +
                    "Return the error not found, if user not registration or news not found in news channel."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody UpsertCommentRequest request){
        Comment newComment = commentsService.save(commentsMapper.requestToComment(request),
                request.getNewsId(),request.getUserId());
        return ResponseEntity.ok(commentsMapper.commentToResponse(newComment));
    }
    @Operation(
            summary = "Update comment",
            description = "Update News, you can change the text comment" +
                    "Changes occur if the user of the comment is correctly specified, he is registered in the news channel"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            )
    })
    @Accessible(checkBy = AccessType.COMMENT)
    @PutMapping("/{id}")
    ResponseEntity<CommentResponse> updateComment(@PathVariable long id, @RequestBody UpsertCommentRequest request){
        Comment updateComment = commentsService.update(commentsMapper.requestToComment(request), id);
        return ResponseEntity.ok(commentsMapper.commentToResponse(updateComment));
    }
    @Operation(
            summary = "Delete comment by Id",
            description = "Delete comment by Id. If user id matches the user id specified in the comment"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204"
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            )
    })
    @Accessible(checkBy = AccessType.COMMENT)
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteComment(@PathVariable long id, long userId){
        commentsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
