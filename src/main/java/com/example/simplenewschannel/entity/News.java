package com.example.simplenewschannel.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id", updatable = false, nullable = false)
    @ToString.Exclude
    private User author;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    private Category category;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @CreationTimestamp
    private Instant timeCreate;
    @UpdateTimestamp
    private Instant timeUpdate;
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    List<Comment> commentsList = new ArrayList<>();

    public void addCommentNews(Comment comment){
        comment.setNews(this);
        commentsList.add(comment);
    }
}
