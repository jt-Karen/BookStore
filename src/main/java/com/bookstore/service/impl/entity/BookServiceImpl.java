package com.bookstore.service.impl.entity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.Book;
import com.bookstore.mapper.entity.BookMapper;
import com.bookstore.service.entity.BookService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    @Transactional
    public DataVO<Book> find() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(bookMapper.selectCount(null));
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.select(Book.class, info ->!info.getColumn().equals("inventory"));//查询指定某字段以外的数据
        List<Book> bookList = bookMapper.selectList(bookQueryWrapper);
        dataVO.setData(bookList);
        return dataVO;
    }

    @Override
    @Transactional
    public DataVO<Book> findInventory() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(bookMapper.selectCount(null));
        List<Book> bookList = bookMapper.selectList(null);
        dataVO.setData(bookList);
        return dataVO;
    }
}
