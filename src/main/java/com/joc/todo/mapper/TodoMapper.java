package com.joc.todo.mapper;

import com.joc.todo.config.MapStructConfig;
import com.joc.todo.dto.TodoDto;
import com.joc.todo.entity.Todo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface TodoMapper {
    Todo toEntity(TodoDto dto);

    TodoDto toDto(Todo entity);

    List<TodoDto> toDtoList(List<Todo> entity);
}
