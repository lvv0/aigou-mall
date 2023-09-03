package com.cskaoyan.config;

import com.cskaoyan.realm.AdminRealm;
import com.cskaoyan.realm.WxRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author ：lww
 * @description：shiro设置
 * @date ：2021/8/13 12:49
 */
@Configuration
public class ShrioConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/admin/auth/login", "anon");      //通常登录请求设置为匿名请求
        filterMap.put("/admin/auth/info", "anon");
        filterMap.put("wx/storage/fetch/**","anon");
        filterMap.put("/wx/auth/login", "anon");      //通常登录请求设置为匿名请求
        filterMap.put("/wx/home/index", "anon");
        filterMap.put("/wx/search/**", "anon");
        filterMap.put("/wx/catalog/**", "anon");
        filterMap.put("/wx/brand/**", "anon");
        filterMap.put("/wx/goods/count", "anon");
        filterMap.put("/wx/goods/list", "anon");
        filterMap.put("/wx/goods/category", "anon");
        filterMap.put("/wx/goods/detail", "anon");
        filterMap.put("/wx/goods/related", "anon");
        filterMap.put("/wx/coupon/list", "anon");
        filterMap.put("/wx/topic/list", "anon");
        filterMap.put("/wx/topic/detail", "anon");
        filterMap.put("/wx/topic/related", "anon");
        filterMap.put("/wx/comment/list", "anon");
        filterMap.put("/wx/groupon/list", "anon");
        filterMap.put("/wx/brand/list", "anon");
        filterMap.put("/wx/brand/detail", "anon");
        filterMap.put("/wx/catalog/index", "anon");
        filterMap.put("/wx/catalog/current", "anon");
        filterMap.put("/wx/cart/goodscount", "anon");
        filterMap.put("/wx/cart/add", "anon");
        filterMap.put("/wx/cart/fastadd", "anon");
        filterMap.put("/wx/collect/addordelete", "anon");

        filterMap.put("/wx/storage/fetch/**", "anon");//访问静态资源文件
        filterMap.put("/**", "authc");        //访问请求，先要执行authc的filter，判断是否是认证状态
        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(AdminRealm realm,
                                                     CustomWebSessionManager sessionManager,
                                                     CustomAuthenticator authenticator) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setAuthenticator(authenticator);
        return securityManager;
    }

    //整合商城应用，跨域了，session变化了
    //sessionManager
    @Bean
    public CustomWebSessionManager sessionManager() {
        CustomWebSessionManager cskaoyanSessionManager = new CustomWebSessionManager();
        cskaoyanSessionManager.setGlobalSessionTimeout(600000);
        cskaoyanSessionManager.setDeleteInvalidSessions(true);
        return cskaoyanSessionManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public CustomAuthenticator authenticator(AdminRealm adminRealm, WxRealm wxRealm){
        CustomAuthenticator customAuthenticator = new CustomAuthenticator();
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        customAuthenticator.setRealms(realms);
        return customAuthenticator;
    }
}
