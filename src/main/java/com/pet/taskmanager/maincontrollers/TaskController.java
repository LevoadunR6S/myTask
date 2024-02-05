package com.pet.taskmanager.maincontrollers;

import com.pet.taskmanager.models.Task;
import com.pet.taskmanager.models.User;
import com.pet.taskmanager.repository.TaskRepository;
import com.pet.taskmanager.repository.UserRepository;
import com.pet.taskmanager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/myTask")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String home() {
        return "index";
    }


    @GetMapping("/my")
    public String account(Model model,
                          @AuthenticationPrincipal UserDetails userDetails,
                          @AuthenticationPrincipal OAuth2User oAuth2User,
                          Principal principal) {

        String username;
        if (oAuth2User != null) {
            // Вхід через OAuth2
            username = oAuth2User.getAttribute("name");
            model.addAttribute("username", username);
        } else {
            username = userDetails.getUsername();
            // Вхід за допомогою юзернейму та паролю
            model.addAttribute("username", username);
        }

        User user = userRepository.findByUsername(username);

        List<Task> tasks = taskService.getTasksByUser(user);

        model.addAttribute("tasks", tasks);


        return "account";
    }

    @GetMapping("/my/create")
    public String createTaskForm() {
        return "create_form";
    }

    @PostMapping("/my/create")
    public String createTask(@RequestParam String header,
                             @RequestParam String description,
                             Principal principal) {
        String username = principal.getName();

        User user = userRepository.findByUsername(username);
        Task task = new Task(header, description, user);

        taskService.addTaskToUser(task);

        return "redirect:/myTask/my";
    }


    @GetMapping("/my/delete/{taskId}")
    public String deleteTask(@PathVariable Long taskId) {
        Task taskToDelete = taskRepository.findById(taskId).orElse(null);
        if (taskToDelete != null) {
            taskRepository.delete(taskToDelete);
        }
        return "redirect:/myTask/my";
    }

    @GetMapping("/my/edit/{taskId}")
    public String editTaskForm(@PathVariable Long taskId, Model model) {
        Task taskToEdit = taskRepository.findById(taskId).orElse(null);


        model.addAttribute("task", taskToEdit);
        return "edit_form";
    }

    @PostMapping("/my/edit/{taskId}")
    public String editTask(@PathVariable Long taskId,
                           @RequestParam String header,
                           @RequestParam String description) {
        Task taskToEdit = taskRepository.findById(taskId).orElse(null);


        if (header != null && !header.isEmpty()) {
            taskToEdit.setHeader(header);
        }

        taskToEdit.setDescription(description);
        taskRepository.save(taskToEdit);
        return "redirect:/myTask/my";
    }

    @GetMapping("myTask/delete/all")
    public String deleteTask() {

            taskRepository.deleteAll();

        return "redirect:/myTask/my";
    }


}
