package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.dto.request.CreateUserRequest;
import com.example.simplenewschannel.entity.User;
import com.example.simplenewschannel.exception.AlreadyExistsException;
import com.example.simplenewschannel.exception.EntityNotFoundException;
import com.example.simplenewschannel.repository.UserRepository;
import com.example.simplenewschannel.security.RefreshTokenService;
import com.example.simplenewschannel.service.UserService;
import com.example.simplenewschannel.utils.BeanUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User findByIdUserAccount(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Пользователь с ID {0} не найден", id)));
    }

    @Override
    public Page<User> findAllAccounts(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findByNameUserAccount(String name) {
        return userRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Пользователь (автор) с таким именем {0} не найден", name)));
    }

    @Override
    public User createNewUserAccount(CreateUserRequest createUserRequest) {
        if (userRepository.existsByName(createUserRequest.getUsername())) {
            throw new AlreadyExistsException("Username already exists!");
        }
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new AlreadyExistsException("Email already exists!");
        }
        var user = User.builder()
                .name(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .roles(createUserRequest.getRoles()).build();
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User updateUserAccount(User upsertUser) {
        User existedUser = userRepository.findById(upsertUser.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Пользователь для обновления с ID {0} не найден", upsertUser.getId())));
        BeanUtils.notNullCopyProperties(upsertUser, existedUser);
        return userRepository.save(existedUser);
    }

    @Override
    public void deleteUserAccount(Long userId) {
        if (refreshTokenService.isHasRefreshTokenByUserId(userId)) {
            refreshTokenService.deleteByUserId(userId);
        }
        userRepository.deleteById(userId);
    }

}
