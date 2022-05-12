package com.bookstore.service.arrival;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.entity.arrival.ArrivalHeader;
import com.bookstore.vo.DataVO;

public interface ArrivalHeaderService extends IService<ArrivalHeader> {
    public DataVO<ArrivalHeader> find();
    public String getPublisherId(Integer number);
    public String getNumberView(Integer number);
}
