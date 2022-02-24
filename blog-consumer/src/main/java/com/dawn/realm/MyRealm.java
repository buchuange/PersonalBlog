package com.dawn.realm;

import com.dawn.model.User;
import com.dawn.service.UserService;
import com.dawn.utils.SpringBeanFactoryUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // 从Shiro中获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        // 创建一个SimpleAuthorizationInfo类的对象，利用这个对象需要设置当前用户的权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        if (userService == null) {
            userService = SpringBeanFactoryUtils.getUserService();
        }

        String type = userService.getType(username);

        Set<String> roles = new HashSet<>();
        roles.add(type);

        // 设置角色信息
        simpleAuthorizationInfo.setRoles(roles);

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        // 获取用户在浏览器中输入的账号
        String username = token.getUsername();

        if (userService == null) {
            userService = SpringBeanFactoryUtils.getUserService();
        }

        User user = userService.checkUser(username);

        if (user == null) {
            throw new UnknownAccountException("账号不存在");
        }

        String dbUsername = user.getUsername();
        String dbPassword = user.getPassword();


        return new SimpleAuthenticationInfo(dbUsername, dbPassword, getName());
    }
}
