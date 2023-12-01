package az.iktlab.taskmanagement.service;

import az.iktlab.taskmanagement.dao.Task;
import az.iktlab.taskmanagement.model.request.TaskCreateRequest;
import az.iktlab.taskmanagement.model.request.TaskUpdateRequest;
import az.iktlab.taskmanagement.model.response.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse create(TaskCreateRequest taskCreateRequest);

    void deleteAllByCategory(String categoryName);

    void deleteById(String id);

    List<TaskResponse> getAllTasks();

    List<TaskResponse> getAllTasksByCategory(String category);

    TaskResponse update(String id,TaskUpdateRequest taskUpdateRequest);
}
