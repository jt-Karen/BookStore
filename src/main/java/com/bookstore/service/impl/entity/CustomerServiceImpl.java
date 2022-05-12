package com.bookstore.service.impl.entity;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.Customer;
import com.bookstore.mapper.entity.CustomerMapper;
import com.bookstore.service.entity.CustomerService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    @Transactional
    public DataVO<Customer> find() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(customerMapper.selectCount(null));
        List<Customer> customerList = customerMapper.selectList(null);
        dataVO.setData(customerList);
        return dataVO;
    }
}
