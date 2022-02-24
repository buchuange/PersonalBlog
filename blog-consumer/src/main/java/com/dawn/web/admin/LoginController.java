package com.dawn.web.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dawn.model.User;
import com.dawn.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Reference(interfaceClass = UserService.class, version = "1.0.0", check = false)
    private UserService userService;


    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }


    @PostMapping("/checkUser")
    public String checkUser(String username, String password, Model model, HttpServletRequest request) {

        // 创建一个shiro的权限操作对象，利用这个对象来完成用户的登录认证
        Subject subject = SecurityUtils.getSubject();
        // 登出方法调用，用于清空登录时的缓存信息，否则无法重新登录
        // 注意：这么做如果用户是误操作也会重新执行一次登录请求
        subject.logout();
        // 判断当前用户是否已经认证（登录）过，如果已经认证过着不需要认证如果没有认证过则进入if完成认证
        if (!subject.isAuthenticated()) {
            // 创建一个用户账号和密码的Token对象（用户认证时的身份令牌），并设值用户从页面传递过来的账号和密码
            // 这个对象将在Shiro中被获取
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                subject.login(token);


                User user = userService.checkUser(username);
                user.setPassword(null);
                subject.getSession().setAttribute("user", user);

         /*       HttpSession session = request.getSession();
                session.setAttribute("user", user);*/

            } catch (UnknownAccountException e) {
                model.addAttribute("errorMessage", "账号不存在，请重新输入");
                return "admin/login";
            } catch (IncorrectCredentialsException e) {
                model.addAttribute("errorMessage", "密码错误，请重新输入");
                return "admin/login";
            }
        }


        return "redirect:/admin/index";
    }

    @GetMapping("/admin/index")
    public String index() {

        return "admin/index";
    }

    @RequestMapping("/noPermission")
    public String noPermission() {

        return "error/noPermission";
    }
}
