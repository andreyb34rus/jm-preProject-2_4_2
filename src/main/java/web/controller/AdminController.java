package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String adminDashboard(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "admin_dashboard";
    }

    @GetMapping("/admin/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/admin/users/{id}")
    public String userProfile(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user";
    }

    @GetMapping("/admin/users/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "user_create_form";
    }

    @PostMapping("/admin/users")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam(value = "selRoles", defaultValue = "ROLE_USER") List<String> roles) {
        userService.setRoles(user, roles);
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user",userService.findById(id));
        return "user_update_form";
    }

    @PostMapping("/admin/users/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") long id,
                         @RequestParam(value = "selRoles", defaultValue = "ROLE_USER") List<String> roles) {
        userService.setRoles(user, roles);
        userService.update(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
