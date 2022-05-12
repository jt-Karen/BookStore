package com.bookstore.service.impl.pick;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.pick.PickData;
import com.bookstore.mapper.pick.PickDataMapper;
import com.bookstore.service.pick.PickDataService;
import org.springframework.stereotype.Service;

@Service
public class PickDataServiceImpl extends ServiceImpl<PickDataMapper, PickData> implements PickDataService {
}
