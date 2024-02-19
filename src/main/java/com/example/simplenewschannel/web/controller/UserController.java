package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.dto.request.PaginationRequest;
import com.example.simplenewschannel.dto.request.UpsertUserRequest;
import com.example.simplenewschannel.dto.response.ModelListResponse;
import com.example.simplenewschannel.dto.response.UserResponse;
import com.example.simplenewschannel.entity.User;
import com.example.simplenewschannel.mapper.UserMapper;
import com.example.simplenewschannel.service.UserService;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<ModelListResponse<UserResponse>> findAll(PaginationRequest request){
        Page<User> userPage = userService.findAll(request.pageRequest());
        return ResponseEntity.ok(ModelListResponse.<UserResponse>builder()
                .totalCount(userPage.getTotalElements())
                .data(userPage.stream().map(userMapper::userToResponse).toList())
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable long id){
        return ResponseEntity.ok(userMapper.userToResponse(userService.findById(id)));
    }
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UpsertUserRequest request){
        User newUser = userService.save(userMapper.requestToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToResponse(newUser));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") long userId, @RequestBody UpsertUserRequest request){
        User updateUser = userService.update(userMapper.requestToUser(userId,request));
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.userToResponse(updateUser));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long userId){
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

}
