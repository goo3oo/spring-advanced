package org.example.expert.domain.todo.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.expert.client.WeatherClient;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.common.util.TodoMapper;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final WeatherClient weatherClient;

    @Transactional
    public TodoSaveResponse saveTodo(AuthUser authUser, TodoSaveRequest todoSaveRequest) {
        User user = User.fromAuthUser(authUser);

        String weather = weatherClient.getTodayWeather();
        // TodoSaveRequest ( Dto -> Entity 변경 메서드 추가, 변경 )
        Todo newTodo = todoSaveRequest.of(weather, user);
        Todo savedTodo = todoRepository.save(newTodo);
        //  TodoMapper 추가, 변경
        return TodoMapper.toTodoSaveResponse(newTodo, weather);
    }

    public Page<TodoResponse> getTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Todo> todos = todoRepository.findAllByOrderByModifiedAtDesc(pageable);
        // TodoMapper 추가, 변경
        return todos.map(TodoMapper::toTodoResponse);
    }

    public TodoResponse getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
            .orElseThrow(() -> new InvalidRequestException("Todo not found"));

        User user = todo.getUser();
        // TodoMapper 추가, 변경
        return TodoMapper.toTodoResponse(todo);
    }

    // 메서드 추가
    public Optional<Todo> findTodoById(Long todoId) {
        return todoRepository.findById(todoId);
    }
}
