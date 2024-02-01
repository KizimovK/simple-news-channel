package com.example.simplenewschannel.service;

import com.example.simplenewschannel.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User findById(Long id);
    User update(User user);
    void deleteById(Long id);
    List<User> findAll();
}
