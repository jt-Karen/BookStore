package com.bookstore.service.impl.sale;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.sale.SaleData;
import com.bookstore.mapper.sale.SaleDataMapper;
import com.bookstore.service.sale.SaleDataService;
import org.springframework.stereotype.Service;

@Service
public class SaleDataServiceImpl extends ServiceImpl<SaleDataMapper, SaleData> implements SaleDataService {
}
