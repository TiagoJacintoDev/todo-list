package com.todolist.task;

import com.todolist.list.ListService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private ListService listService;

    private final String NOT_FOUND_BODY = "Task not found.";
    private final String DELETED_BODY = "Task successfully deleted.";

    @PostMapping("/{listId}")
    public ResponseEntity<Object> saveTask(@PathVariable(value = "listId") UUID listId,@RequestBody @Valid TaskDto taskDto){
        var taskModel = new TaskModel();
        BeanUtils.copyProperties(taskDto, taskModel);
        taskModel.setPriority(String.valueOf(taskDto.priority()));
        var listModelOptional = listService.findById(listId);

        if(listModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List not found.");
        }

        taskModel.setListModel(listModelOptional.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskModel));
    }

    @GetMapping
    public ResponseEntity<List<TaskModel>> getAllTasks(){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTask(@PathVariable(value = "id") UUID id){
        Optional<TaskModel> taskModelOptional = taskService.findById(id);
        if (taskModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable(value = "id") UUID id){
        Optional<TaskModel> parkingSpotModelOptional = taskService.findById(id);
        if (parkingSpotModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        taskService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(DELETED_BODY);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable(value = "id") UUID id,
                                                           @RequestBody @Valid TaskDto taskDto){
        Optional<TaskModel> taskModelOptional = taskService.findById(id);
        if (taskModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        var taskModel = new TaskModel();
        BeanUtils.copyProperties(taskDto, taskModel);
        taskModel.setId(taskModelOptional.get().getId());
        taskModel.setPriority(String.valueOf(taskDto.priority()));
        return ResponseEntity.status(HttpStatus.OK).body(taskService.save(taskModel));
    }
}