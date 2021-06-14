package web.services;

import web.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(long id);
    void save(User user);
    void update(User updatedUser);
    void delete(long id);
    void setRoles(User user, List<String> roles);
}
