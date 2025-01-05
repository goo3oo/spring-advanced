package org.example.expert.domain.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.user.dto.response.UserResponse;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static CommentResponse from(Comment comment) {
        UserResponse user = new UserResponse(comment.getUser().getId(),
            comment.getUser().getEmail());
        return new CommentResponse(
            comment.getId(),
            comment.getContents(),
            user
        );
    }
}
