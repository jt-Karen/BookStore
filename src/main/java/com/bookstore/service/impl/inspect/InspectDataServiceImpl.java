package com.bookstore.service.impl.inspect;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.inspect.InspectData;
import com.bookstore.mapper.inspect.InspectDataMapper;
import com.bookstore.service.inspect.InspectDataService;
import org.springframework.stereotype.Service;

@Service
public class InspectDataServiceImpl extends ServiceImpl<InspectDataMapper, InspectData> implements InspectDataService {
}
