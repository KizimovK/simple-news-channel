package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.dto.request.UserRequest;
import com.example.simplenewschannel.dto.response.UserListResponse;
import com.example.simplenewschannel.dto.response.UserResponse;
import com.example.simplenewschannel.entity.User;
import com.example.simplenewschannel.mapper.UserMapper;
import com.example.simplenewschannel.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<UserListResponse> findAll(){
        return ResponseEntity.ok(userMapper.userListResponseList(userService.findAll()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable long id){
        return ResponseEntity.ok(userMapper.userToResponse(userService.findById(id)));
    }
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request){
        User newUser = userService.save(userMapper.requestToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToResponse(newUser));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") long userId, @RequestBody UserRequest request){
        User updateUser = userService.update(userMapper.requestToUser(userId,request));
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.userToResponse(updateUser));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long userId){
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

}
