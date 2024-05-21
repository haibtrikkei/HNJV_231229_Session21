package ra.demo_springwebmvc_hibernate.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ra.demo_springwebmvc_hibernate.dao.RoleDAO;
import ra.demo_springwebmvc_hibernate.model.entity.Role;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Role getRoleByRoleName(String roleName) {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("from Role where roleName=:roleName", Role.class)
                    .setParameter("roleName", roleName)
                    .getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }
}
