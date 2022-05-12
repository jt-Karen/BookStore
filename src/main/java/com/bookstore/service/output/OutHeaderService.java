package com.bookstore.service.output;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.entity.output.OutHeader;
import com.bookstore.vo.DataVO;

public interface OutHeaderService extends IService<OutHeader> {
    public DataVO<OutHeader> find();
}
