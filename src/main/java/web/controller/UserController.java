package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UserController {

	private UserDao userDao;
	private RoleDao roleDao;

	@Autowired
	public void setDao(UserDao userDao, RoleDao roleDao) {
		this.userDao = userDao;
		this.roleDao = roleDao;
	}

	@GetMapping("/admin")
	public String adminDashboard(Principal principal, Model model) {
		model.addAttribute("username", principal.getName());
		return "admin_dashboard";
	}

	@GetMapping("/admin/users")
	public String allUsers(Model model) {
		model.addAttribute("users", userDao.findAll());
		return "users";
	}

	@GetMapping("/admin/users/{id}")
	public String userProfile(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", userDao.findById(id));
		return "user";
	}

	@GetMapping("/admin/users/new")
	public String newUser(@ModelAttribute("user") User user) {
		return "user_create_form";
	}

	@PostMapping("/admin/users")
	public String saveUser(@ModelAttribute("user") User user,
			@RequestParam(value = "selRoles", defaultValue = "ROLE_USER") List<String> roles) {
		Set<Role> roleSet = new HashSet<>();
		for (String role : roles) {
			Role r = roleDao.findByRole(role);
			roleSet.add(r);
		}
		user.setRoles(roleSet);
		userDao.save(user);
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/users/{id}/edit")
	public String editUser(@PathVariable("id") long id, Model model) {
		model.addAttribute("user",userDao.findById(id));
		return "user_update_form";
	}

	@PostMapping("/admin/users/{id}")
	public String update(@ModelAttribute("user") User user,
			@PathVariable("id") long id,
			@RequestParam(value = "selRoles", defaultValue = "ROLE_USER") List<String> roles) {
		Set<Role> roleSet = new HashSet<>();
		for (String role : roles) {
			Role r = roleDao.findByRole(role);
			roleSet.add(r);
		}
		user.setRoles(roleSet);
		userDao.update(user);
		return "redirect:/admin/users";
	}

	@PostMapping("/admin/users/{id}/delete")
	public String delete(@PathVariable("id") long id) {
		userDao.delete(id);
		return "redirect:/admin/users";
	}

	@GetMapping("/user")
	public String userProfile() {
		return "user";
	}

	@RequestMapping(value = "hello", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring MVC-SECURITY application");
		messages.add("5.2.0 version by sep'19 ");
		model.addAttribute("messages", messages);
		return "hello";
	}

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
}