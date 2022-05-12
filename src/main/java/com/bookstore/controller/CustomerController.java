package com.bookstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookstore.entity.Customer;
import com.bookstore.mapper.entity.CustomerMapper;
import com.bookstore.service.entity.CustomerService;
import com.bookstore.service.pick.PickHeaderService;
import com.bookstore.service.sale.SaleHeaderService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SaleHeaderService saleHeaderService;
    @Autowired
    private PickHeaderService pickHeaderService;
    @Autowired
    private CustomerMapper customerMapper;

    @RequestMapping("/customerdata")
    @ResponseBody
    public DataVO getCustomer(){
        return customerService.find();
    }

    @RequestMapping("/customer")
    public String getView() {
        return "customer";
    }

    @RequestMapping("/setcustomer")
    public String getViewSet() {
        return "setCustomer";
    }

    @RequestMapping("/editcustomer")
    public String getViewEdit() {
        return "editCustomer";
    }

    @RequestMapping("/getcustomer")
    @ResponseBody
    public Customer getCustomer(@RequestParam Integer number){
        Integer CustomerId = saleHeaderService.getCustomerId(number);
        Customer customer = customerService.getById(CustomerId);
        return customer;
    }

    @RequestMapping("/getcustomerdata")
    @ResponseBody
    public Customer getCustomerData(@RequestParam Integer number){
        Customer customer = customerService.getById(number);
        return customer;
    }

    @RequestMapping("/getcustomerpick")
    @ResponseBody
    public Customer getCustomerPick(@RequestParam Integer number){
        Integer CustomerId = pickHeaderService.getCustomerId(number);
        Customer customer = customerService.getById(CustomerId);
        return customer;
    }

    @RequestMapping("/customerdatasearch")
    @ResponseBody
    public DataVO getCustomerSearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(customerMapper.selectCount(null));
        QueryWrapper<Customer> customerQueryWrapper = new QueryWrapper<>();
        customerQueryWrapper.like("customer_id",search).or().like("name",search).or().like("tel",search).or().like("address",search);
        List<Customer> customerList = customerMapper.selectList(customerQueryWrapper);
        dataVO.setData(customerList);
        return dataVO;
    }

    @RequestMapping("/submitcustomer")
    @ResponseBody
    public void submitCustomer(@RequestParam String customerData){
        JSONObject obj = JSON.parseObject(customerData);
        String customerName = (String) obj.get("customerName");
        String customerTel = (String) obj.get("customerTel");
        String customerAddress = (String) obj.get("customerAddress");
        Customer customer = new Customer();
        customer.setName(customerName);
        customer.setTel(customerTel);
        customer.setAddress(customerAddress);
        customerService.save(customer);
    }

    @RequestMapping("/updatecustomer")
    @ResponseBody
    public void updateCustomer(@RequestParam Integer number, @RequestParam String customerData){
        JSONObject obj = JSON.parseObject(customerData);
        String customerName = (String) obj.get("customerName");
        String customerTel = (String) obj.get("customerTel");
        String customerAddress = (String) obj.get("customerAddress");
        Customer customer = new Customer();
        customer.setCustomerId(number);
        customer.setName(customerName);
        customer.setTel(customerTel);
        customer.setAddress(customerAddress);
        customerService.updateById(customer);
    }

    @RequestMapping("/delcustomer")
    @ResponseBody
    public void delCustomer(@RequestParam Integer number){
        QueryWrapper<Customer> customerDataQueryWrapper = new QueryWrapper<>();
        customerDataQueryWrapper.eq("customer_id",number);
        customerService.remove(customerDataQueryWrapper);
    }
}
