package ru.antoncharov.billing.mapper;

import org.mapstruct.Mapper;
import ru.antoncharov.billing.dto.UserDto;
import ru.antoncharov.billing.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserEntity user);
}
