package com.bookstore.service.pick;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.entity.pick.PickHeader;
import com.bookstore.vo.DataVO;

public interface PickHeaderService extends IService<PickHeader> {
    public DataVO<PickHeader> find();
    public DataVO<PickHeader> findQuery();
    public Integer getCustomerId(Integer number);
    public String getNumberView(Integer number);
}
