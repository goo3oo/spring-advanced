package org.example.expert.domain.manager.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.common.util.ManagerMapper;
import org.example.expert.domain.common.util.TodoValidator;
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest;
import org.example.expert.domain.manager.dto.response.ManagerResponse;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.manager.repository.ManagerRepository;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.service.TodoService;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

    // TodoRepository, UserRepository 주입 -> TodoService, UserService 주입
    private final ManagerRepository managerRepository;
    private final TodoService todoService;
    private final UserService userService;

    @Transactional
    public ManagerResponse saveManager(
        AuthUser authUser,
        long todoId,
        ManagerSaveRequest managerSaveRequest
    ) {
        User user = User.fromAuthUser(authUser);
        
        Todo todo = todoService.findTodoById(todoId)
            .orElseThrow(() -> new InvalidRequestException("Todo not found"));

        TodoValidator.validateUserIsNotNull(todo);

        // TodoValidator 추가, 변경
        TodoValidator.validateUserIsTodoCreator(user, todo);

        User managerUser = userService.findUserById(managerSaveRequest.getManagerUserId());
        // TodoValidator 추가, 변경
        TodoValidator.validateManagerIsTodoCreator(user, managerUser);

        Manager newManagerUser = new Manager(managerUser, todo);
        Manager savedManagerUser = managerRepository.save(newManagerUser);
        // ManagerMapper 추가, 변경
        return ManagerMapper.from(newManagerUser);
    }

    public List<ManagerResponse> getManagers(long todoId) {
        Todo todo = todoService.findTodoById(todoId)
            .orElseThrow(() -> new InvalidRequestException("Todo not found"));

        List<Manager> managerList = managerRepository.findAllByTodoId(todo.getId());
        // 스트림으로 변경
        return managerList.stream()
            .map(manager -> {
                User user = manager.getUser();
                return ManagerMapper.from(manager);
            })
            .toList();
    }

    @Transactional
    public void deleteManager(long userId, long todoId, long managerId) {
        User user = userService.findUserById(userId);

        Todo todo = todoService.findTodoById(todoId)
            .orElseThrow(() -> new InvalidRequestException("Todo not found"));
        // TodoValidator 추가, 변경
        TodoValidator.validateUserIsTodoCreator(user, todo);

        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new InvalidRequestException("Manager not found"));
        // TodoValidator 추가, 변경
        TodoValidator.validateTodoManger(todo, manager);

        managerRepository.delete(manager);
    }
}
