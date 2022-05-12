package com.bookstore.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.entity.order.OrderHeader;
import com.bookstore.vo.DataVO;

public interface OrderHeaderService extends IService<OrderHeader> {
    public DataVO<OrderHeader> find();
    public String getPublisherId(Integer number);
}
