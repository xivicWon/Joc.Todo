package com.joc.todo.data.mapper;

import com.joc.todo.config.MapStructConfig;
import com.joc.todo.data.dto.TodoDto;
import com.joc.todo.data.entity.Todo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface TodoMapper {
    Todo toEntity(TodoDto dto);

    TodoDto toDto(Todo entity);

    List<TodoDto> toDtoList(List<Todo> entity);
}
