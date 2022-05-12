package com.bookstore.service.impl.input;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.input.InData;
import com.bookstore.mapper.input.InDataMapper;
import com.bookstore.service.input.InDataService;
import org.springframework.stereotype.Service;

@Service
public class InDataServiceImpl extends ServiceImpl<InDataMapper, InData> implements InDataService {
}
