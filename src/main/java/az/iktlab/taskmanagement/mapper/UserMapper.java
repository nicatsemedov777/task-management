package az.iktlab.taskmanagement.mapper;

import az.iktlab.taskmanagement.dao.User;
import az.iktlab.taskmanagement.model.request.UserCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserCreateRequest userCreateRequest);

}
