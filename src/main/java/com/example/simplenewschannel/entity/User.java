package com.example.simplenewschannel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, unique = true)
    private String name;
    private String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<News> newsUser;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentNews> commentsUser;

    public void addNews(News news){
        newsUser.add(news);
    }
    public void addComment(CommentNews commentNews){
        commentsUser.add(commentNews);
    }

}
