package web.dao;

import web.model.Role;

public interface RoleDao {
    Role findByRole(String role);
}
