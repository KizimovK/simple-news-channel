package com.example.simplenewschannel.event;

import com.example.simplenewschannel.dto.request.UpsertCategoryRequest;
import com.example.simplenewschannel.dto.request.UpsertCommentRequest;
import com.example.simplenewschannel.dto.request.UpsertNewsRequest;
import com.example.simplenewschannel.dto.request.UpsertUserRequest;
import com.example.simplenewschannel.mapper.CategoryMapper;
import com.example.simplenewschannel.mapper.CommentsMapper;
import com.example.simplenewschannel.mapper.NewsMapper;
import com.example.simplenewschannel.mapper.UserMapper;
import com.example.simplenewschannel.service.CategoryService;
import com.example.simplenewschannel.service.CommentsService;
import com.example.simplenewschannel.service.NewsService;
import com.example.simplenewschannel.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "app.example", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
public class StartExamplesData {
    private static final String USER_EXAMPLE_FILE = "data/userExample.json";
    private static final String CATEGORY_EXAMPLE_FILE = "data/categoryExample.json";
    private static final String NEWS_EXAMPLE_FILE = "data/newsExample.json";
    private static final String COMMENT_EXAMPLE_FILE = "data/commentExample.json";

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;
    private final UserService userService;
    private final UserMapper userMapper;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final NewsService newsService;
    private final NewsMapper newsMapper;
    private final CommentsService commentsService;
    private final CommentsMapper commentsMapper;


    @EventListener(ApplicationStartedEvent.class)
    public void loadDataExample() {
        log.info("Load example users, categories news, news and comment for this news, all previous entries will be deleted");
        loadExampleUser();
        loadExampleCategory();
        loadExampleNews();
        loadExampleComment();
    }

    private void loadExampleUser() {
        userService.deleteAll();
        List<UpsertUserRequest> usersExampleList = loadResourceFromJson(USER_EXAMPLE_FILE)
                .stream()
                .map(lhm -> UpsertUserRequest.builder()
                        .name(lhm.get(UpsertUserRequest.Fields.name))
                        .email(lhm.get(UpsertUserRequest.Fields.email))
                        .build()
                ).toList();
        usersExampleList.forEach(u -> userService.save(userMapper.requestToUser(u)));
        log.info("Load examples users count {}", usersExampleList.size());
    }

    private void loadExampleCategory() {
        categoryService.deleteAll();
        List<UpsertCategoryRequest> categoryExampleList = loadResourceFromJson(CATEGORY_EXAMPLE_FILE)
                .stream()
                .map(lhm -> UpsertCategoryRequest.builder()
                        .name(lhm.get(UpsertCategoryRequest.Fields.name))
                        .build()
                ).toList();
        categoryExampleList.forEach(c -> categoryService.create(categoryMapper.requestToCategory(c)));
        log.info("Load examples category count {}", categoryExampleList.size());
    }

    private void loadExampleNews() {
        List<UpsertNewsRequest> newsExampleList = loadResourceFromJson(NEWS_EXAMPLE_FILE)
                .stream()
                .map(lhm -> UpsertNewsRequest.builder()
                        .authorName(lhm.get(UpsertNewsRequest.Fields.authorName))
                        .categoryName(lhm.get(UpsertNewsRequest.Fields.categoryName))
                        .title(lhm.get(UpsertNewsRequest.Fields.title))
                        .content(lhm.get(UpsertNewsRequest.Fields.content)).build()
                ).toList();
        newsExampleList.forEach(n -> newsService.save(newsMapper.requestToNews(n), n.getAuthorName(), n.getCategoryName()));
        log.info("Load examples news count {}", newsExampleList.size());
    }

    private void loadExampleComment() {
        List<Long> idAllNewsList = newsService.getAllIdNews();
        List<Long> idAllUserList = userService.getAllIdUser();
        List<UpsertCommentRequest> commentExampleList = loadResourceFromJson(COMMENT_EXAMPLE_FILE)
                .stream()
                .map(lhm -> UpsertCommentRequest.builder()
                        .userId(idAllUserList.get(new Random().nextInt(idAllUserList.size())))
                        .newsId(idAllNewsList.get(new Random().nextInt(idAllNewsList.size())))
                        .commentText(lhm.get(UpsertCommentRequest.Fields.commentText))
                        .build()
                ).toList();
        commentExampleList.forEach(c ->
                commentsService.save(commentsMapper.requestToComment(c), c.getNewsId(), c.getUserId()));
        log.info("Load examples comments count {}", commentExampleList.size());
    }

    private List<LinkedHashMap<String, String>> loadResourceFromJson(String file) {
        List<LinkedHashMap<String, String>> exampleDataList = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:" + file);
        URL url;
        try {
            url = resource.getURL();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (InputStream inputStream = url != null ? url.openStream() : new FileInputStream(resource.getFile())
        ) {
            TypeReference<List<LinkedHashMap<String, String>>> typeRef = new TypeReference<>() {
            };
            exampleDataList = objectMapper.readValue(inputStream, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return exampleDataList;
    }
}
