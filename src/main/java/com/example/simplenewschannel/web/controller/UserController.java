package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.aop.Accessible;
import com.example.simplenewschannel.dto.request.PaginationRequest;
import com.example.simplenewschannel.dto.request.UpsertUserRequest;
import com.example.simplenewschannel.dto.response.ExceptionResponse;
import com.example.simplenewschannel.dto.response.ModelListResponse;
import com.example.simplenewschannel.dto.response.UserResponse;
import com.example.simplenewschannel.entity.User;
import com.example.simplenewschannel.mapper.UserMapper;
import com.example.simplenewschannel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;


    @Operation(
            summary = "Get all user",
            description = "Get all users from registration news-channel"
    )

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ModelListResponse<UserResponse> findAll(@Valid PaginationRequest request) {
        Page<User> userPage = userService.findAll(request.pageRequest());
        return ModelListResponse.<UserResponse>builder()
                .totalCount(userPage.getTotalElements())
                .data(userPage.stream().map(userMapper::userToResponse).toList())
                .build();
    }

    @Operation(
            summary = "Get User by Id",
            description = "Get User by Id. Return id, name and email"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findById(@PathVariable long id) {
        return userMapper.userToResponse(userService.findById(id));
    }

    @Operation(
            summary = "Create User",
            description = "Add user"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UpsertUserRequest request) {
        User newUser = userService.save(userMapper.requestToUser(request));
        return userMapper.userToResponse(newUser);
    }

    @Operation(
            summary = "Update User",
            description = "Changes user news-channel"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            )
    })
    @Accessible(checkBy = AccessType.USER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable long id, @RequestBody UpsertUserRequest request) {
        User updateUser = userService.update(userMapper.requestToUser(id, request));
        return userMapper.userToResponse(updateUser);
    }

    @Operation(
            summary = "Delete user by Id",
            description = "Delete user by Id"
    )
    @Accessible(checkBy = AccessType.USER)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    public void deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
    }

}
