package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.aop.Accessible;
import com.example.simplenewschannel.dto.request.CreateUserRequest;
import com.example.simplenewschannel.dto.request.PaginationRequest;
import com.example.simplenewschannel.dto.request.UpsertUserRequest;
import com.example.simplenewschannel.dto.response.ModelListResponse;
import com.example.simplenewschannel.dto.response.UserResponse;
import com.example.simplenewschannel.entity.User;
import com.example.simplenewschannel.mapper.UserMapper;
import com.example.simplenewschannel.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ModelListResponse<UserResponse> findAllUserAccounts(@Valid PaginationRequest request) {
        Page<User> userPage = userService.findAllAccounts(request.pageRequest());
        return ModelListResponse.<UserResponse>builder()
                .totalCount(userPage.getTotalElements())
                .data(userPage.stream().map(userMapper::userToResponse).toList())
                .build();
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerAccountForNewUser(@RequestBody CreateUserRequest request) {
        return userMapper.userToResponse(userService.createNewUserAccount(request));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    @Accessible(checkBy = AccessType.USER, availableForModerator = true)
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findByUserIdAccount(@PathVariable long userId) {
        return userMapper.userToResponse(userService.findByIdUserAccount(userId));
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{userId}/update")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    @Accessible(checkBy = AccessType.USER, availableForModerator = true)
    public UserResponse updateUserAccount(@PathVariable long userId, @RequestBody UpsertUserRequest request) {
        return userMapper.userToResponse(userService.updateUserAccount(userMapper.requestToUser(userId, request)));
    }


    @DeleteMapping("/{userId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    @Accessible(checkBy = AccessType.USER, availableForModerator = true)
    public void deleteUserAccount(@PathVariable long userId) {
        userService.deleteUserAccount(userId);
    }
}
