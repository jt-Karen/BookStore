package com.bookstore.service.entity;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.entity.Book;
import com.bookstore.vo.DataVO;

public interface BookService extends IService<Book> {
    public DataVO<Book> find();
    public DataVO<Book> findInventory();
}
