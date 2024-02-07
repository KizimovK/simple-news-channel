package com.example.simplenewschannel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 50, unique = true)
    private String newsCategory;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<News> newsCategoryList;
}
