package com.example.simplenewschannel.service;

import com.example.simplenewschannel.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User save(User user);
    User findById(Long id);
    User findByName(String name);
    User update(User user);
    void deleteById(Long id);
    Page<User> findAll(Pageable pageable);
}
