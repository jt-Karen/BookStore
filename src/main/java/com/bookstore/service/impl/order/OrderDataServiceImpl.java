package com.bookstore.service.impl.order;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.order.OrderData;
import com.bookstore.mapper.order.OrderDataMapper;
import com.bookstore.service.order.OrderDataService;
import org.springframework.stereotype.Service;

@Service
public class OrderDataServiceImpl extends ServiceImpl<OrderDataMapper, OrderData> implements OrderDataService {
}
