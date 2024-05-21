package ra.demo_springwebmvc_hibernate.config;

import org.springframework.web.servlet.HandlerInterceptor;
import ra.demo_springwebmvc_hibernate.model.entity.Role;
import ra.demo_springwebmvc_hibernate.model.entity.RoleName;
import ra.demo_springwebmvc_hibernate.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user==null){
            response.sendRedirect("/demo_session20/login");
            return false;
        }else{
            if(user.getRoleSet().stream().anyMatch(role->role.getRoleName().equals(RoleName.ROLE_ADMIN.toString()))){
                return true;
            }else{
                response.sendRedirect("/demo_session20/403");
                return false;
            }
        }
    }
}
