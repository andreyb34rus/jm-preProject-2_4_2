package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDaoImp implements RoleDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Role findByRole(String role) {
        return entityManager.createQuery(
                "select r from Role r left join fetch r.users where r.role like :role", Role.class)
                .setParameter("role", role)
                    .getSingleResult();
    }
}
