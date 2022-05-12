package com.bookstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bookstore.entity.Administrator;
import com.bookstore.service.entity.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    @RequestMapping("/administrator")
    public String getView() {
        return "administrator";
    }

    @RequestMapping("/getadministratordata")
    @ResponseBody
    public Administrator getAdministratorData(@RequestParam Integer number){
        Administrator administrator = administratorService.getById(number);
        return administrator;
    }

    @RequestMapping("/updateadministrator")
    @ResponseBody
    public void submitAdministrator(@RequestParam String administratorData){
        JSONObject obj = JSON.parseObject(administratorData);
        String account = (String) obj.get("account");
        String password = (String) obj.get("password");
        String department = (String) obj.get("department");
        String authority = (String) obj.get("authority");
        Integer authorityInt = Integer.parseInt(authority);
        Administrator administrator = new Administrator();
        administrator.setAccount(account);
        administrator.setPassword(password);
        administrator.setDepartment(department);
        administrator.setAuthority(authorityInt);
        administratorService.updateById(administrator);
    }
}
