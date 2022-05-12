package com.bookstore.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookstore.entity.Administrator;
import com.bookstore.mapper.entity.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
public class LoginController {
    @Autowired
    private AdministratorMapper administratorMapper;

    @RequestMapping( value = "/logindata")
    @ResponseBody
    public Map<String,Object> getData(@RequestParam("account") String account , @RequestParam("password") String password){
        Administrator administrator = administratorMapper.selectOne(new QueryWrapper<Administrator>().eq("account",account).eq("password",password));
        Integer authority = administrator.getAuthority();
        Map<String,Object> data = new HashMap();
        if(administrator != null){
            data.put("code",1);
            if(authority == 1){
                data.put("url","/homesale");
            }else if(authority == 2){
                data.put("url","/homepurchase");
            }else if(authority == 3){
                data.put("url","/homeinput");
            }else if(authority == 4){
                data.put("url","/homeoutput");
            }
        }else {
            data.put("code",0);
        }
        return data;
    }

    @RequestMapping("/login")
    public String getView() {
        return "login";
    }

    @RequestMapping("/homesale")
    public String getViewHomeSale() {
        return "homeSale";
    }

    @RequestMapping("/homepurchase")
    public String getViewHomePurchase() {
        return "homePurchase";
    }

    @RequestMapping("/homeinput")
    public String getViewHomeInput() {
        return "homeInput";
    }

    @RequestMapping("/homeoutput")
    public String getViewHomeOutput() {
        return "homeOutput";
    }
}
