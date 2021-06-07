package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public User findByUsername(String username) {
        return entityManager.createQuery(
                "select u from User u left join fetch u.roles where u.username like :username", User.class)
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

    @Transactional
    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Transactional
    @Override
    public void update(User updatedUser) {
        entityManager.merge(updatedUser);
    }

    @Transactional
    @Override
    public void delete(long id) {
        entityManager.remove(findById(id));
    }
}
