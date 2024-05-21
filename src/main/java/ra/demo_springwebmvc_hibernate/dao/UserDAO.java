package ra.demo_springwebmvc_hibernate.dao;

import ra.demo_springwebmvc_hibernate.model.entity.User;

public interface UserDAO {
    public boolean saveUser(User user);
    public User getUserByUserNameAndPass(String username, String password);
}
