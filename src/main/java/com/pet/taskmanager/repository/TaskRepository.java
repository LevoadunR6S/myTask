package com.pet.taskmanager.repository;

import com.pet.taskmanager.models.Task;
import com.pet.taskmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

}
