package com.bookstore.service.impl.arrival;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.arrival.ArrivalData;
import com.bookstore.mapper.arrival.ArrivalDataMapper;
import com.bookstore.service.arrival.ArrivalDataService;
import org.springframework.stereotype.Service;

@Service
public class ArrivalDataServiceImpl extends ServiceImpl<ArrivalDataMapper, ArrivalData> implements ArrivalDataService {
}
