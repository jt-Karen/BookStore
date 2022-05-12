package com.bookstore.service.input;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.entity.input.InHeader;
import com.bookstore.vo.DataVO;

public interface InHeaderService extends IService<InHeader> {
    public DataVO<InHeader> find();
}
