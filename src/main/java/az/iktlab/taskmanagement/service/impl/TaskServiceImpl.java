package az.iktlab.taskmanagement.service.impl;

import az.iktlab.taskmanagement.dao.Category;
import az.iktlab.taskmanagement.dao.Task;
import az.iktlab.taskmanagement.dao.enums.TaskPriority;
import az.iktlab.taskmanagement.dao.enums.TaskStatus;
import az.iktlab.taskmanagement.errors.exception.ResourceAlreadyExistException;
import az.iktlab.taskmanagement.errors.exception.ResourceNotFoundException;
import az.iktlab.taskmanagement.mapper.TaskMapper;
import az.iktlab.taskmanagement.model.UserInfo;
import az.iktlab.taskmanagement.model.request.TaskCreateRequest;
import az.iktlab.taskmanagement.model.request.TaskUpdateRequest;
import az.iktlab.taskmanagement.model.response.TaskResponse;
import az.iktlab.taskmanagement.reposiroty.CategoryRepository;
import az.iktlab.taskmanagement.reposiroty.TaskRepository;
import az.iktlab.taskmanagement.service.TaskService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    @Resource(name = "requestScopedUser")
    private final UserInfo current;
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public TaskResponse create(TaskCreateRequest taskCreateRequest) {
        Category category = categoryRepository.findByName(taskCreateRequest.getCategoryName())
                .orElse(Category.builder()
                        .name(taskCreateRequest.getCategoryName())
                        .build());
        Task task = buildTask(taskCreateRequest, category);
        task.setUser(current.getUser());
        taskRepository.save(task);
        return buildTaskResponse(task);
    }

    @Override
    public void deleteAllByCategory(String categoryName) {
        taskRepository.deleteAllByCategoryName(categoryName);
    }

    @Override
    public void deleteById(String id) {
        taskRepository.deleteByIdSoft(id);
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAllByUserId(current.getUser().getId()).orElseThrow(ResourceAlreadyExistException::new)
                .stream().map(TaskServiceImpl::buildTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> getAllTasksByCategory(String categoryName) {
        return taskRepository.findAllByCategoryName(categoryName).orElseThrow(ResourceNotFoundException::new)
                .stream()
                .map(TaskServiceImpl::buildTaskResponse).collect(Collectors.toList());
    }

    @Override
    public TaskResponse update(String id, TaskUpdateRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not fount with this id :" + id));

        task.setName(request.getName().isEmpty() ? task.getName() : request.getName());
        task.setDescription(request.getDescription().isEmpty() ? task.getDescription() : request.getDescription());

        if (TaskPriority.match(request.getPriority()))
            task.setPriority(TaskPriority.getByLabel(request.getPriority()));

        if (TaskStatus.match(request.getStatus()))
            task.setStatus(TaskStatus.getByLabel(request.getStatus()));

        if (!(request.getCategoryName().isEmpty())) {
            Category category = categoryRepository.findByName(request.getCategoryName()).orElse(Category.builder()
                    .name(request.getCategoryName()).build());
            categoryRepository.save(category);
            task.setCategory(category);
        }
        if(request.getStatus().equals("DONE")){
            task.setIsCompleted(true);
        }
        taskRepository.save(task);
        return buildTaskResponse(task);
    }

    private Task buildTask(TaskCreateRequest taskCreateRequest, Category category) {
        categoryRepository.save(category);
        taskCreateRequest.setCategory(category);
        return TaskMapper.INSTANCE.taskCreateRequestToTask(taskCreateRequest);
    }

    private static TaskResponse buildTaskResponse(Task task) {
        return TaskMapper.INSTANCE.taskEntityToTaskResponse(task);
    }
}
