package org.example.expert.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveRequest {

  @NotBlank
  private String contents;

  // 중복코드 제거: Dto -> Entity 메서드 추가
  public Comment of(User user, Todo todo) {
    return new Comment(contents, user, todo);
  }
}
