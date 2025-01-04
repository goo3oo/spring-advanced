package org.example.expert.domain.manager.dto.response;

import org.example.expert.domain.user.dto.response.UserResponse;

public record ManagerResponse(
    Long id,
    UserResponse user
) {

}
