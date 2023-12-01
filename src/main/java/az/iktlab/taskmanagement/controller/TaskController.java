package az.iktlab.taskmanagement.controller;

import az.iktlab.taskmanagement.model.request.TaskCreateRequest;
import az.iktlab.taskmanagement.model.request.TaskUpdateRequest;
import az.iktlab.taskmanagement.model.response.TaskResponse;
import az.iktlab.taskmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public TaskResponse create(@RequestBody TaskCreateRequest taskCreateRequest) {
        return taskService.create(taskCreateRequest);
    }

    @DeleteMapping
    public HttpStatus deleteAllByCategory(@RequestParam String categoryName) {
        taskService.deleteAllByCategory(categoryName);
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteById(@PathVariable String id) {
        taskService.deleteById(id);
        return HttpStatus.OK;
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/category")
    public List<TaskResponse> getAllByCategory(@RequestParam String category) {
        return taskService.getAllTasksByCategory(category);
    }

    @PutMapping("{id}")
    public TaskResponse update(@PathVariable String id,@RequestBody TaskUpdateRequest taskUpdateRequest) {
        return taskService.update(id,taskUpdateRequest);
    }
}
