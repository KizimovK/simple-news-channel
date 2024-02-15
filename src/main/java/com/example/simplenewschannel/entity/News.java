package com.example.simplenewschannel.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;
    @Column(columnDefinition = "VARCHAR(255)")
    private String title;
//    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;
    @CreationTimestamp
    private Instant timeCreate;
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    List<CommentNews> commentsNews;

    public void addCommentNews(CommentNews commentNews){
        commentsNews.add(commentNews);
    }
}
