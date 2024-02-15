package com.example.simplenewschannel.mapper;

import com.example.simplenewschannel.dto.request.UserRequest;
import com.example.simplenewschannel.dto.response.UserListResponse;
import com.example.simplenewschannel.dto.response.UserResponse;
import com.example.simplenewschannel.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserResponse userToResponse(User user);
    @Mapping(source = "userId", target = "id")
    User requestToUser(long userId, UserRequest request);
    User requestToUser(UserRequest request);

    default UserListResponse userListResponseList(List<User> usersList){
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.setUsers(usersList.stream().map(this::userToResponse).collect(Collectors.toList()));
        return userListResponse;
    }
}
