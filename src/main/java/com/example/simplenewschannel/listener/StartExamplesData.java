package com.example.simplenewschannel.listener;

import com.example.simplenewschannel.dto.request.UpsertCategoryRequest;
import com.example.simplenewschannel.dto.request.UpsertCommentRequest;
import com.example.simplenewschannel.dto.request.UpsertNewsRequest;
import com.example.simplenewschannel.dto.request.UpsertUserRequest;
import com.example.simplenewschannel.entity.Comment;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "evn.example", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
public class StartExamplesData {
    private static final String USER_EXAMPLE_FILE = "data/userExample.json";
    private static final String CATEGORY_EXAMPLE_FILE = "data/categoryExample.json";
    private static final String NEWS_EXAMPLE_FILE = "data/newsExample.json";
    private static final String COMMENT_EXAMPLE_FILE = "data/commentExample.json";

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
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
        List<UpsertUserRequest> usersExesmpleList = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:" + USER_EXAMPLE_FILE);
        userService.deleteAll();

        try (InputStream inputStream = new FileInputStream(resource.getFile())) {
            TypeReference<List<UpsertUserRequest>> typeRef = new TypeReference<>() {
            };
            usersExesmpleList = objectMapper.readValue(inputStream, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        usersExesmpleList.forEach(u -> userService.save(userMapper.requestToUser(u)));
        log.info("Load examples users count {}", usersExesmpleList.size());
    }

    private void loadExampleCategory() {
        List<UpsertCategoryRequest> categoryExampleList = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:" + CATEGORY_EXAMPLE_FILE);
        categoryService.deleteAll();
        try (InputStream inputStream = new FileInputStream(resource.getFile())) {
            TypeReference<List<UpsertCategoryRequest>> typeRef = new TypeReference<>() {
            };
            categoryExampleList = objectMapper.readValue(inputStream, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        categoryExampleList.forEach(c -> categoryService.create(categoryMapper.requestToCategory(c)));
        log.info("Load examples category count {}", categoryExampleList.size());
    }

    private void loadExampleNews() {
        List<UpsertNewsRequest> newsExampleList = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:" + NEWS_EXAMPLE_FILE);
        try (InputStream inputStream = new FileInputStream(resource.getFile())) {
            TypeReference<List<UpsertNewsRequest>> typeRef = new TypeReference<>() {
            };
            newsExampleList = objectMapper.readValue(inputStream, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newsExampleList.forEach(n -> newsService.save(newsMapper.requestToNews(n), n.getAuthorName(), n.getCategoryName()));
        log.info("Load examples news count {}", newsExampleList.size());
    }

    private void loadExampleComment() {

        List<Long> idAllNewsList = newsService.getAllIdNews();
        List<Long> idAllUserList = userService.getAllIdUser();
        List<UpsertCommentRequest> commentExampleList;
        Resource resource = resourceLoader.getResource("classpath:" + COMMENT_EXAMPLE_FILE);
        try (InputStream inputStream = new FileInputStream(resource.getFile())) {
            TypeReference<List<UpsertCommentRequest>> typeRef = new TypeReference<>() {
            };
            commentExampleList = objectMapper.readValue(inputStream, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        commentExampleList.forEach(c -> {
            c.setUserId(idAllUserList.get(new Random().nextInt(0, idAllUserList.size() - 1)));
            c.setNewsId(idAllNewsList.get(new Random().nextInt(0, idAllNewsList.size() - 1)));
            commentsService.save(commentsMapper.requestToComment(c),c.getNewsId(),c.getUserId());
        });
        log.info("Load examples comments count {}",commentExampleList.size());
    }


}
