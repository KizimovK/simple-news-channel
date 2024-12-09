package com.example.simplenewschannel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private long id;
    private String name;
    private String email;
}
