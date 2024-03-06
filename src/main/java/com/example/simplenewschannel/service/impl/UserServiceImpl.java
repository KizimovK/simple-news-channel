package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.entity.User;
import com.example.simplenewschannel.exception.EntityExistsException;
import com.example.simplenewschannel.exception.EntityNotFoundException;
import com.example.simplenewschannel.repository.UserRepository;
import com.example.simplenewschannel.service.UserService;
import com.example.simplenewschannel.utils.BeanUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        if (userRepository.existsByName(user.getName())) {
            throw new EntityExistsException(
                    MessageFormat.format("Пользователь с таким именем {0} уже существует!", user.getName()));
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EntityExistsException(
                    MessageFormat.format("Пользователь с такой почтой {0} уже существует!", user.getName()));
        }
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Пользователь с ID {0} не найден", id)));
    }

    @Override
    public User update(User user) {
        User existedUser = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Пользователь для обновления с ID {0} не найден", user.getId())));
        BeanUtils.notNullCopyProperties(user, existedUser);
        return userRepository.save(existedUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name).orElseThrow(()-> new EntityNotFoundException(
                MessageFormat.format("Пользователь (автор) с таким именем {0} не найден", name)));
    }
    public void deleteAll(){
        userRepository.deleteAll();
    }

    @Override
    public List<Long> getAllIdUser() {
        return userRepository.findAllId();
    }
}
