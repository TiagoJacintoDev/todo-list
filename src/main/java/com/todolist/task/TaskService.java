package com.todolist.task;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public TaskModel save(TaskModel taskModel) {
        return taskRepository.save(taskModel);
    }

    public List<TaskModel> findAll() {
        return taskRepository.findAll();
    }

    public Optional<TaskModel> findById(UUID id) {
        return taskRepository.findById(id);
    }

    @Transactional
    public void delete(TaskModel taskModel) {
        taskRepository.delete(taskModel);
    }
}
