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
    @Column(length = 50)
    private String login;
    @Column(name = "first_name", length = 50)
    private String firstName;
    @Column(name = "last_name", length = 50)
    private String lastName;
    private String email;
    @Column(length = 12)
    private String phone;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<News> newsUser;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentNews> commentsUser;

}
