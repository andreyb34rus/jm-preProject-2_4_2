package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.dao.UserDao;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

	private UserDao userDao;

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
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