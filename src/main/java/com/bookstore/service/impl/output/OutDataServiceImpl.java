package com.bookstore.service.impl.output;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.output.OutData;
import com.bookstore.mapper.output.OutDataMapper;
import com.bookstore.service.output.OutDataService;
import org.springframework.stereotype.Service;

@Service
public class OutDataServiceImpl extends ServiceImpl<OutDataMapper, OutData> implements OutDataService {
}
