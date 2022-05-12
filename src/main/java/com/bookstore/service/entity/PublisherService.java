package com.bookstore.service.entity;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.entity.Publisher;
import com.bookstore.vo.DataVO;

public interface PublisherService extends IService<Publisher> {
    public DataVO<Publisher> find();
}
