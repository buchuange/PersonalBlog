package com.dawn.config;

import com.dawn.realm.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    // 配置一个自定义的Realm的bean，最终将使用这个bean返回的对象来完成我们的认证和授权
    @Bean
    public Realm myRealm() {
        MyRealm realm = new MyRealm();
        return realm;
    }


    // 配置Shiro的安全管理器
    @Bean
    public SecurityManager securityManager(Realm realm) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);

        return securityManager;
    }

    // 配置一个Shiro的过滤器bean，这个bean将配置Shiro相关的一个规则的拦截
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("/admin/index");
        shiroFilter.setUnauthorizedUrl("/noPermission");

        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("/checkUser", "anon"); // 配置登录请求不需要认证，anno表示某个请求不需要认证
        map.put("/logout", "logout"); // 配置登录的请求，登出后会清空当前用户的内存（释放session）

        map.put("/static/**", "anon");


        map.put("/admin/**", "authc,roles[admin]");

        // 设置权限拦截规则
        shiroFilter.setFilterChainDefinitionMap(map);

        return shiroFilter;
    }
}
