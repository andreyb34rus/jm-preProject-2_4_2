package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    User findByUsername(String username);
    List<User> findAll();
    User findById(long id);
    void save(User user);
    void update(User updatedUser);
    void delete(long id);
}
