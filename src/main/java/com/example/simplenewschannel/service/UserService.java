package com.example.simplenewschannel.service;

import com.example.simplenewschannel.dto.request.CreateUserRequest;
import com.example.simplenewschannel.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User findByIdUserAccount(Long id);
    User findByNameUserAccount(String name);
    Page<User> findAllAccounts(Pageable pageable);
    User createNewUserAccount(CreateUserRequest createUserRequest);
    User updateUserAccount(User upsertUser);
    void deleteUserAccount(Long userId);

}
