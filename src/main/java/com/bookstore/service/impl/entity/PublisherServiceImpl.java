package com.bookstore.service.impl.entity;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.Publisher;
import com.bookstore.mapper.entity.PublisherMapper;
import com.bookstore.service.entity.PublisherService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublisherServiceImpl extends ServiceImpl<PublisherMapper, Publisher> implements PublisherService {
    @Autowired
    private PublisherMapper publisherMapper;

    @Override
    @Transactional
    public DataVO<Publisher> find() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(publisherMapper.selectCount(null));
        List<Publisher> publisherList = publisherMapper.selectList(null);
        dataVO.setData(publisherList);
        return dataVO;
    }
}
