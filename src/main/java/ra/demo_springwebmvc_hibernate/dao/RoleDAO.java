package ra.demo_springwebmvc_hibernate.dao;

import ra.demo_springwebmvc_hibernate.model.entity.Role;

public interface RoleDAO {
    public Role getRoleByRoleName(String roleName);
}
