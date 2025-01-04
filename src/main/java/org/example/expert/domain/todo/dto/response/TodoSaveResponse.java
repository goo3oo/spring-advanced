package org.example.expert.domain.todo.dto.response;

import org.example.expert.domain.user.dto.response.UserResponse;

public record TodoSaveResponse(
    Long id,
    String title,
    String contents,
    String weather,
    UserResponse user
) {

}
