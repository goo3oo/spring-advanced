package org.example.expert.domain.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.dto.response.UserResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoMapper {

    public static TodoResponse toTodoResponse(Todo todo) {
        UserResponse user = new UserResponse(todo.getUser().getId(), todo.getUser().getEmail());
        return new TodoResponse(
            todo.getId(),
            todo.getTitle(),
            todo.getContents(),
            todo.getWeather(),
            user,
            todo.getCreatedAt(),
            todo.getModifiedAt()
        );
    }

    public static TodoSaveResponse toTodoSaveResponse(Todo todo, String weather) {
        UserResponse user = new UserResponse(todo.getUser().getId(), todo.getUser().getEmail());
        return new TodoSaveResponse(
            todo.getId(),
            todo.getTitle(),
            todo.getContents(),
            weather,
            user
        );
    }

}
