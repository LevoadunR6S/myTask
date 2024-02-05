package com.pet.taskmanager.maincontrollers;

import com.pet.taskmanager.models.User;
import com.pet.taskmanager.services.AuthService;
import com.pet.taskmanager.services.MailService;
import com.pet.taskmanager.services.UserService;
import com.pet.taskmanager.services.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {


    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private MailService mailService;


    @Autowired
    private PasswordEncoder passwordEncoder;


    //Контроллери реєстрації
    @GetMapping("/signup")
    public String signup(Model model) {
        return "register";
    }

    @PostMapping("/signup")
    public String processingSignUp(@RequestParam String cPassword, User user, Model model) {
        if (!(cPassword.equals(user.getPassword()))) {
            model.addAttribute("error", "Passwords is not same");
            return "register";
        }

        String result = authService.addNewUser(user);

        if (result == null) {
            model.addAttribute("message", "User exist");
            return "register";
        }
        return result;
    }

    /////////////////////////////////////////////////////////////


    //Контроллери відновлення паролю
    @GetMapping("/forgot_password")
    public String forgotPassword() {
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processingForgotPassword(@RequestParam String email, HttpServletRequest request, Model model) {
        String token = RandomString.make(30);
        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            mailService.sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "forgot_password_form";
    }


    @GetMapping("/reset_password")
    public String passwordForm(@Param(value = "token") String token, Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String cPassword = request.getParameter("cPassword");

        if (!(cPassword.equals(password))) {
            model.addAttribute("message", "Passwords is not same");
            return "reset_password_form";
        }

        User user = userService.getByResetPasswordToken(token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            userService.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";
    }
    //////////////////////////////////////////////////////////////////////////////
}
