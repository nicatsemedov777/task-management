package az.iktlab.taskmanagement

import az.iktlab.taskmanagement.dao.Category
import az.iktlab.taskmanagement.dao.Task
import az.iktlab.taskmanagement.model.UserInfo
import az.iktlab.taskmanagement.model.request.TaskCreateRequest
import az.iktlab.taskmanagement.model.request.TaskUpdateRequest
import az.iktlab.taskmanagement.reposiroty.CategoryRepository
import az.iktlab.taskmanagement.reposiroty.TaskRepository
import az.iktlab.taskmanagement.service.TaskService
import az.iktlab.taskmanagement.service.impl.TaskServiceImpl
import org.springframework.boot.test.mock.mockito.MockBean
import spock.lang.Specification
import spock.lang.Subject

class TaskServiceTest extends Specification{
    @Subject
    TaskService taskService

    @MockBean
    UserInfo userInfo

    @MockBean
    TaskRepository taskRepository

    @MockBean
    CategoryRepository categoryRepository

    def setup() {
        taskService = new TaskServiceImpl(userInfo, taskRepository, categoryRepository)
    }

    def "Test create method"() {
        given:
        def taskCreateRequest = new TaskCreateRequest(categoryName: "TestCategory", /* other fields */)
        def category = new Category(name: "TestCategory")
        def task = new Task(/* set required fields */)
        categoryRepository.findByName(taskCreateRequest.getCategoryName()) >> Optional.of(category)
        categoryRepository.save(category) >> category
        taskRepository.save(task) >> task

        when:
        def createdTask = taskService.create(taskCreateRequest)

        then:
        createdTask != null
    }

    def "Test deleteAllByCategory method"() {
        given:
        def categoryName = "TestCategory"

        when:
        taskService.deleteAllByCategory(categoryName)

        then:
        1 * taskRepository.deleteAllByCategoryName(categoryName)
    }

    // ... (Previous setup remains the same)

    def "Test deleteById method"() {
        given:
        def taskId = "taskId"

        when:
        taskService.deleteById(taskId)

        then:
        1 * taskRepository.deleteByIdSoft(taskId)
    }

    def "Test getAllTasks method"() {
        given:
        def userId = "userId"
        def tasks = [new Task(/* Task details */)]
        taskRepository.findAllByUserId(userId) >> Optional.of(tasks)

        when:
        def allTasks = taskService.getAllTasks()

        then:
        allTasks.size() == 1
        // Add more assertions as needed to validate the result
    }

    def "Test getAllTasksByCategory method"() {
        given:
        def categoryName = "TestCategory"
        def tasks = [new Task(/* Task details */)]
        taskRepository.findAllByCategoryName(categoryName) >> Optional.of(tasks)

        when:
        def tasksByCategory = taskService.getAllTasksByCategory(categoryName)

        then:
        tasksByCategory.size() == 1
        // Add more assertions as needed to validate the result
    }

    def "Test update method"() {
        given:
        def taskId = "taskId"
        def taskUpdateRequest = new TaskUpdateRequest(/* Update fields */)
        def existingTask = new Task(/* Existing task details */)
        taskRepository.findById(taskId) >> Optional.of(existingTask)
        categoryRepository.findByName(taskUpdateRequest.getCategoryName()) >> Optional.of(new Category())

        when:
        def updatedTask = taskService.update(taskId, taskUpdateRequest)

        then:
        updatedTask != null
        // Add more assertions as needed to validate the updated task
    }


}
