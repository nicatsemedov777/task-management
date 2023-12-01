package az.iktlab.taskmanagement.mapper;

import az.iktlab.taskmanagement.dao.Task;
import az.iktlab.taskmanagement.model.request.TaskCreateRequest;
import az.iktlab.taskmanagement.model.response.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    Task taskCreateRequestToTask(TaskCreateRequest taskCreateRequest);

    TaskResponse taskEntityToTaskResponse(Task task);
}
