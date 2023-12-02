package az.iktlab.taskmanagement.controller;

import az.iktlab.taskmanagement.model.request.TaskCreateRequest;
import az.iktlab.taskmanagement.model.request.TaskUpdateRequest;
import az.iktlab.taskmanagement.model.response.TaskResponse;
import az.iktlab.taskmanagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "This endpoint help us to add new tasks",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The request was successful"),
                    @ApiResponse(responseCode = "400",
                            description = "There is incoming request validation error"),
                    @ApiResponse(responseCode = "409",
                            description = "There is a conflict with the current state of the resource, " +
                                    "preventing the request from being completed."),
                    @ApiResponse(responseCode = "417",
                            description = "The server cannot meet the expectations specified in the request"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server.")
            })
    public TaskResponse create(@RequestBody TaskCreateRequest taskCreateRequest) {
        return taskService.create(taskCreateRequest);
    }

    @DeleteMapping
    @Operation(summary = "This endpoint help us to delete tasks by category name ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The request was successful"),
                    @ApiResponse(responseCode = "400",
                            description = "There is incoming request validation error"),
                    @ApiResponse(responseCode = "409",
                            description = "There is a conflict with the current state of the resource, " +
                                    "preventing the request from being completed."),
                    @ApiResponse(responseCode = "417",
                            description = "The server cannot meet the expectations specified in the request"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server.")
            })
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
    @Operation(summary = "This endpoint help us to update task",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The request was successful"),
                    @ApiResponse(responseCode = "400",
                            description = "There is incoming request validation error"),
                    @ApiResponse(responseCode = "409",
                            description = "There is a conflict with the current state of the resource, " +
                                    "preventing the request from being completed."),
                    @ApiResponse(responseCode = "417",
                            description = "The server cannot meet the expectations specified in the request"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server.")
            })
    public TaskResponse update(@PathVariable String id,@RequestBody TaskUpdateRequest taskUpdateRequest) {
        return taskService.update(id,taskUpdateRequest);
    }
}
