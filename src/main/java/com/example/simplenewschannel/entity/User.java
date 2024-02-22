package com.example.simplenewschannel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, unique = true, nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<News> newsList = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comment> commentsList = new ArrayList<>();

    public void addNews(News news){
        news.setAuthor(this);
        newsList.add(news);
    }
    public void addComment(Comment comment){
        comment.setUser(this);
        commentsList.add(comment);
    }

}
