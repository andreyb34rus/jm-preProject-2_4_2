package web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private UserDao userDao;
    private RoleDao roleDao;

    @Autowired
    public UserServiceImp(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void update(User updatedUser) {
        userDao.update(updatedUser);
    }

    @Override
    public void delete(long id) {
        userDao.delete(id);
    }

    @Override
    public void setRoles(User user, List<String> roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            Role r = roleDao.findByRole(role);
            roleSet.add(r);
        }
        user.setRoles(roleSet);
    }
}
