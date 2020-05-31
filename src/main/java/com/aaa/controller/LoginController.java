package com.aaa.controller;

import com.aaa.biz.MenuBiz;
import com.aaa.biz.UserBiz;
import com.aaa.entity.LayUiTree;
import com.aaa.entity.User;
import com.aaa.shiro.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private MenuBiz menuBiz;

    @Autowired
    private UserBiz userBiz;

    //登录路径
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
    //登录
    @RequestMapping("/login")
    public String login(String login_name,String password, Model model){
        System.out.println(login_name+password);
        Subject subject = SecurityUtils.getSubject();
        //要把输入的密码转换成加密的密码在传
        User user = userBiz.selectUserByUsername(login_name);
        if(user==null) {
            model.addAttribute("message","用户名错误");
            return "login";
        }
        //获取盐值
        String salt = user.getSalt();
        //有了盐值就可以获取加密后的密码
        String saltPassword = ShiroUtil.encryptionBySalt(salt,password);
        //用户名和加密后的密码封装成token
        UsernamePasswordToken token= new UsernamePasswordToken(login_name,saltPassword);
        try {
            //调用doGetAuthenticationInfo开始认证
            subject.login(token);
        }catch (UnknownAccountException e){
            model.addAttribute("message","用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("message","密码错误");
            return "login";
        }
        model.addAttribute("login_name",login_name);
        List<LayUiTree> menus = menuBiz.selectAllMenuByName(login_name);
        System.out.println(menus);
        model.addAttribute("menus",menus);
        return "user/index";
    }
    /**
     * 注销
     * @return
     */
    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
