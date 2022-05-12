package com.bookstore.service.impl.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.order.OrderHeader;
import com.bookstore.mapper.order.OrderHeaderMapper;
import com.bookstore.service.order.OrderHeaderService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderHeaderServiceImpl extends ServiceImpl<OrderHeaderMapper, OrderHeader> implements OrderHeaderService {
    @Autowired
    private OrderHeaderMapper orderHeaderMapper;

    @Override
    @Transactional
    public DataVO<OrderHeader> find() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(orderHeaderMapper.selectCount(null));
        QueryWrapper<OrderHeader> orderHeaderQueryWrapper = new QueryWrapper<>();
        orderHeaderQueryWrapper.select(OrderHeader.class, info ->!info.getColumn().equals("publisher_id"));//查询指定某字段以外的数据
        List<OrderHeader> orderHeaderList = orderHeaderMapper.selectList(orderHeaderQueryWrapper);
        dataVO.setData(orderHeaderList);
        return dataVO;
    }

    @Override
    @Transactional
    public String getPublisherId(Integer number) {
        OrderHeader orderHeader = orderHeaderMapper.selectById(number);
        String PublisherId = orderHeader.getPublisherId();
        return PublisherId;
    }
}
