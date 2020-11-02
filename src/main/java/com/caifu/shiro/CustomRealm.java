package com.caifu.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class CustomRealm extends AuthorizingRealm{



    /*@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        String loginName = user.getLoginName();
        //根据用户名去数据库查询用户信息
        SysUser user = loginService.getUserByName(loginName);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            for (Permissions permissions : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
            }
        }
        return simpleAuthorizationInfo;
    }*/

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        try {
            //获取登录用户名
            /*SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
            //添加角色和权限
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

            List<SysMenu> list = menuService.getMenuByUser(user.getUserNo());
            for (SysMenu menu : list) {
                //添加角色
                //simpleAuthorizationInfo.addRole(role.getRoleName());
                //添加权限
                simpleAuthorizationInfo.addStringPermission(menu.getPermissionCode());
            }
            return simpleAuthorizationInfo;*/
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }

        //获取用户的输入的账号.
        String userNo = upToken.getUsername();
        String openId = String.valueOf(upToken.getPassword());

        //获取用户信息
//
//        SysWechatUser user = null;
//        try{
//            user = sysWechatUserService.getUserByField("OPEN_ID", openId);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        if (user == null) {
//            //这里返回后会报出对应异常
//            throw new UnknownAccountException("账户不存在!");
//        } else {
//            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getOpenId(), getName());
//            return info;
//        }

        return null;
    }

    /**
     * 密码验证器
     * @param credentialsMatcher
     */
    /*@Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtil.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtil.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }*/
}
