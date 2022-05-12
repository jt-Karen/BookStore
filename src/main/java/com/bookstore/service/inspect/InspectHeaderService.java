package com.bookstore.service.inspect;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.entity.inspect.InspectHeader;
import com.bookstore.vo.DataVO;

public interface InspectHeaderService extends IService<InspectHeader> {
    public DataVO<InspectHeader> find();
    public DataVO<InspectHeader> findQuery();
    public String getNumberView(Integer number);
}
