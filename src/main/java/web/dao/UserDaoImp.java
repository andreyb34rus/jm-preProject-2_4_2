package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User findByUsername(String username) {
        return entityManager.createQuery(
                "select u from User u where u.username like :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u").getResultList();
    }

    @Override
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User updatedUser) {
        entityManager.merge(updatedUser);
    }

    @Override
    public void delete(long id) {
        entityManager.remove(findById(id));
    }
}
