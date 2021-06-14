package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.User;
import web.services.UserServiceImp;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

	private UserDao userDao;

	@Autowired
	public UserController(UserDao userDao) {
		this.userDao = userDao;
	}

	@GetMapping("/user")
	public String userProfile(Principal principal, Model model) {
		model.addAttribute("user", userDao.findByUsername(principal.getName()));
		return "user";
	}
}