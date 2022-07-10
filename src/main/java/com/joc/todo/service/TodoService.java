package com.joc.todo.service;


import com.joc.todo.entity.Todo;
import com.joc.todo.exception.ApplicationException;
import com.joc.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


// 계층형 아키텍쳐, (Layered Architecture)
// @Controller, @Repository, @Service
@Service
// final이 있는 필드는 자동으로 의존성 주입을 해준다.
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> getList(String userId) {
        return todoRepository.findByUserId(userId);
    }

    @Transactional
    public void create(Todo todo) {
        log.debug(">>> todo : {}", todo);///.=]
        validateTodo(todo);
        todoRepository.save(todo);
        log.info("Todo가 등록되었습니다. {}", todo.getId());
    }

    private void validateTodo(Todo todo) {
        if (todo == null) {
            String msg = "Todo is null";
            log.error(msg);
            throw new ApplicationException(msg);
        }
        if (todo.getUserId() == null) {
            String msg2 = "Userid is not setting in Todo";
            log.error(msg2);
            throw new ApplicationException(msg2);
        }
    }

    @Transactional
    public void update(Todo newTodo) {
        validateTodo(newTodo);
        Optional<Todo> oldTodo = todoRepository.findById(newTodo.getId());
        log.debug(">>> newTodo : {}", newTodo);
        log.debug(">>> oldTodo : {}", oldTodo);
        oldTodo.ifPresentOrElse(
                todo -> {
                    todo.setTitle(newTodo.getTitle());
                    todo.setDone(newTodo.isDone());
                    todoRepository.save(todo);
                    log.info("Todo가 수정되었습니다. {}", todo.getId());
                },
                () -> log.warn("수정할 Todo가 없습니다. {}", newTodo.getId())
        );
    }

    @Transactional
    public void delete(Todo todo) {
        log.debug(">>> todo : {}", todo);
        validateTodo(todo);
        todoRepository.delete(todo);
        log.info("Todo가 삭제되었습니다. {}", todo.getId());
    }
}
