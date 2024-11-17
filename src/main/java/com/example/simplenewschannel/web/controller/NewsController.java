package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.aop.Accessible;
import com.example.simplenewschannel.dto.request.FilterNewsRequest;
import com.example.simplenewschannel.dto.request.PaginationRequest;
import com.example.simplenewschannel.dto.request.UpsertNewsRequest;
import com.example.simplenewschannel.dto.response.*;
import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.mapper.NewsMapper;
import com.example.simplenewschannel.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@Tag(name = "News", description = "News API")
public class NewsController {
    private final NewsService newsService;
    private final NewsMapper newsMapper;

    public NewsController(NewsService newsService, NewsMapper newsMapper) {
        this.newsService = newsService;
        this.newsMapper = newsMapper;
    }
    @Operation(
            summary = "Get all news by filter",
            description = "Get all news by Id. Return id, name category, name author, title, content, " +
                    "time create and update news and count comments this news." +
                    "If the category and author of the news is not specified, then get all news"
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
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    public ModelListResponse<BriefNewsResponse> filterBy(@Valid PaginationRequest paginationRequest,
                                                                         FilterNewsRequest filterRequest){
        Page<News> filterNews = newsService.filterBy(paginationRequest, filterRequest);
        return ModelListResponse.<BriefNewsResponse>builder()
                .totalCount(filterNews.getTotalElements())
                .data(filterNews.stream().map(newsMapper::newsToBriefResponse).toList())
                .build();
    }
    @Operation(
            summary = "Get news by Id",
            description = "Get news by Id. Return id, name category, name author, title, content, time create and update news and comments this news"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    public NewsResponse findById(@PathVariable long id){
        return newsMapper.newsToResponse(newsService.findById(id));
    }
    @Operation(
            summary = "Create News",
            description = "Add entity News, comprising: id, author name(user), category name, title, context create time." +
                    "Return the error not found, if author name not registration or category name not found in news channel."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
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
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    public NewsResponse createNews(@RequestBody UpsertNewsRequest request,
                                   @AuthenticationPrincipal UserDetails userDetails){
        News newNews = newsService.save(newsMapper.requestToNews(request)
                ,userDetails.getUsername(), request.getCategoryName());
        return newsMapper.newsToResponse(newNews);
    }
    @Operation(
            summary = "Update News",
            description = "Update News,you can change the title, " +
                    "the text of the News, and the time of the news update is also recorded. " +
                    "Changes occur if the author of the news is correctly specified, he is registered in the news channel"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
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
    @Accessible(checkBy = AccessType.NEWS)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    public NewsResponse updateNews(@PathVariable(name = "id") long idNews,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   @RequestBody UpsertNewsRequest request){
        News updateNews = newsService.updateNews(newsMapper.requestToNews(request), idNews) ;
        return newsMapper.newsToResponse(updateNews);
    }
    @Operation(
            summary = "Delete News by Id",
            description = "Delete news by Id"
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
    @Accessible(checkBy = AccessType.NEWS)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<Void> deleteNewsById(@PathVariable("id") long idNews,
                                               @AuthenticationPrincipal UserDetails userDetails){
        newsService.deleteById(idNews);
        return ResponseEntity.noContent().build();
    }


}
