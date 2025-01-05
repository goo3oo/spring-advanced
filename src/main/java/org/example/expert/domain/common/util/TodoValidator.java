package org.example.expert.domain.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.User;
import org.springframework.util.ObjectUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoValidator {

    public static void validateUserIsNotNull(Todo todo) {
        if (todo.getUser() == null) {
            throw new InvalidRequestException("담당자를 등록하려고 하는 유저가 일정을 만든 유저가 유효하지 않습니다.");
        }
    }

    public static void validateUserIsTodoCreator(User user, Todo todo) {
        if (!ObjectUtils.nullSafeEquals(user.getId(), todo.getUser().getId())) {
            throw new InvalidRequestException("담당자를 등록하려고 하는 유저가 일정을 만든 유저가 유효하지 않습니다.");
        }
    }

    public static void validateManagerIsTodoCreator(User user, User managerUser) {
        if (ObjectUtils.nullSafeEquals(user.getId(), managerUser.getId())) {
            throw new InvalidRequestException("일정 작성자는 본인을 담당자로 등록할 수 없습니다.");
        }
    }

    public static void validateTodoCreator(User user, Todo todo) {
        if (todo.getUser() == null || !ObjectUtils.nullSafeEquals(user.getId(),
            todo.getUser().getId())) {
            throw new InvalidRequestException("해당 일정을 만든 유저가 유효하지 않습니다.");
        }
    }

    public static void validateTodoManger(Todo todo, Manager manager) {
        if (!ObjectUtils.nullSafeEquals(todo.getId(), manager.getTodo().getId())) {
            throw new InvalidRequestException("해당 일정에 등록된 담당자가 아닙니다.");
        }
    }
}



