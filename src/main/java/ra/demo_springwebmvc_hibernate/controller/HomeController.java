package ra.demo_springwebmvc_hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ra.demo_springwebmvc_hibernate.dao.UserDAO;
import ra.demo_springwebmvc_hibernate.model.dto.LoginForm;
import ra.demo_springwebmvc_hibernate.model.entity.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
public class HomeController {
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = {"/register"})
    public String register(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "register";
    }

    @RequestMapping("save")
    public String save(@ModelAttribute("user")User user, @RequestParam("avatarFiles")MultipartFile avatarFiles, HttpServletRequest request, Model model){
        //xu ly upload file
        String path = request.getServletContext().getRealPath("images");

        File f = new File(path);
        File destination = new File(path+"/"+avatarFiles.getOriginalFilename());
        if(!destination.exists()){
            try {
                FileCopyUtils.copy(avatarFiles.getBytes(),destination);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        user.setAvatar(avatarFiles.getOriginalFilename());

        boolean bl = userDAO.saveUser(user);
        if(bl){
            return "home";
        }else{
            model.addAttribute("err","Create user failed!");
            model.addAttribute("user",user);
            return "register";
        }
    }

    @RequestMapping(value = {"/","/login"},method = RequestMethod.GET)
    public String initLogin(Model model, HttpServletRequest request){
        LoginForm obj = new LoginForm();
        //xu ly cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies){
            if(c.getName().equals("username")){
                obj.setUsername(c.getValue());
            }
            if(c.getName().equals("password")){
                obj.setPassword(c.getValue());
            }
            if(c.getName().equals("save")){
                obj.setSave(c.getValue());
            }
        }
        model.addAttribute("obj",obj);
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String doLogin(@ModelAttribute LoginForm obj, Model model, HttpSession session, HttpServletResponse response){
        User user = userDAO.getUserByUserNameAndPass(obj.getUsername(), obj.getPassword());
        if(user!=null){
            obj.setPassword("");
            session.setAttribute("user",user);
            session.setMaxInactiveInterval(10*60);

            Cookie cUser, cPass,cSave;
            if(obj.getSave()!=null && !obj.getSave().isEmpty()){
                cUser = new Cookie("username",obj.getUsername());
                cPass = new Cookie("password",obj.getPassword());
                cSave = new Cookie("save","checked");
            }else{
                cUser = new Cookie("username","");
                cPass = new Cookie("password","");
                cSave = new Cookie("save","");
            }

            //thiet lap thoi gian cho cookie
            cUser.setMaxAge(7*24*60*60);
            cPass.setMaxAge(7*24*60*60);
            cSave.setMaxAge(7*24*60*60);

            response.addCookie(cUser);
            response.addCookie(cPass);
            response.addCookie(cSave);

            return "home";
        }else{
            model.addAttribute("err","Login failed!");
            model.addAttribute("obj",obj);
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session, Model model){
        session.invalidate();
        return "redirect:/login";
    }

    @RequestMapping("/403")
    public String _403(){
        return "403";
    }

}
