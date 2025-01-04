package org.example.expert.domain.todo.dto.response;

import org.example.expert.domain.user.dto.response.UserResponse;

import java.time.LocalDateTime;

public record TodoResponse(
    Long id,
    String title,
    String contents,
    String weather,
    UserResponse user,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {

}
