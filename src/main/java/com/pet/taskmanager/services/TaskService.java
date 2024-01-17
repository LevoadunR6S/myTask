package com.pet.taskmanager.services;

import com.pet.taskmanager.models.Task;
import com.pet.taskmanager.models.User;
import com.pet.taskmanager.repository.TaskRepository;
import com.pet.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public void addTaskToUser(Task task) {
        taskRepository.save(task);
    }


}
