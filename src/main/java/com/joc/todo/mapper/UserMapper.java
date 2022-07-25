package com.joc.todo.mapper;

import com.joc.todo.config.MapStructConfig;
import com.joc.todo.dto.UserDto;
import com.joc.todo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto dto);


    @Mapping(target = "password", ignore = true)
    UserDto toDto(User entity);

}
