package com.joc.todo.mapper;

import com.joc.todo.config.MapStructConfig;
import com.joc.todo.dto.UserDto;
import com.joc.todo.entity.User;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface UserMapper {
    User toEntity(UserDto dto);

    UserDto toDto(User entity);

}
