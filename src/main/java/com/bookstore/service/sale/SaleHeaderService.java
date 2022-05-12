package com.bookstore.service.sale;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.entity.sale.SaleHeader;
import com.bookstore.vo.DataVO;

public interface SaleHeaderService extends IService<SaleHeader> {
    public DataVO<SaleHeader> find();
    public DataVO<SaleHeader> findQuery();
    public Integer getCustomerId(Integer number);
    public String getNumberView(Integer number);
}
