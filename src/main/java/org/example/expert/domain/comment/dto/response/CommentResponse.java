package org.example.expert.domain.comment.dto.response;

import org.example.expert.domain.user.dto.response.UserResponse;

public record CommentResponse(
    Long id,
    String contents,
    UserResponse user
) {

}
