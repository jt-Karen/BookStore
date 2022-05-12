package com.bookstore.service.entity;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.entity.Customer;
import com.bookstore.vo.DataVO;

public interface CustomerService extends IService<Customer> {
    public DataVO<Customer> find();
}
