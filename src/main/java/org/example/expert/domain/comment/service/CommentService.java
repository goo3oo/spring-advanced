package org.example.expert.domain.comment.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.comment.repository.CommentRepository;
import org.example.expert.domain.common.util.CommentMapper;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.service.TodoService;
import org.example.expert.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    // TodoRepository -> TodoService 주입
    private final CommentRepository commentRepository;
    private final TodoService todoService;

    @Transactional
    public CommentResponse saveComment(
        AuthUser authUser,
        long todoId,
        CommentSaveRequest commentSaveRequest
    ) {
        User user = User.fromAuthUser(authUser);
        Todo todo = todoService.findTodoById(todoId)
            .orElseThrow(() -> new InvalidRequestException("Todo not found"));
        // commentSaveRequest ( Dto -> Entity 메서드 추가, 변경 )
        Comment newComment = commentSaveRequest.of(user, todo);
        Comment savedComment = commentRepository.save(newComment);
        // CommentMapper 추가, 변경
        return CommentMapper.from(savedComment);
    }

    public List<CommentResponse> getComments(long todoId) {
        List<Comment> commentList = commentRepository.findAllByTodoId(todoId);
        // 스트림으로 변환
        return commentList.stream()
            .map(comment -> {
                User user = comment.getUser();
                return CommentMapper.from(comment);
            })
            .toList();
    }
}
