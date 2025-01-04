package org.example.expert.domain.todo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoSaveRequest {

  @NotBlank
  private String title;

  @NotBlank
  private String contents;

  // 중복코드 제거: DTO -> Entity 변환 메서드 추가
  public Todo of(String weather, User user) {
    return new Todo(title, contents, weather, user);
  }
}
