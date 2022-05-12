package com.bookstore.service.impl.sale;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.sale.SaleHeader;
import com.bookstore.mapper.sale.SaleHeaderMapper;
import com.bookstore.service.sale.SaleHeaderService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleHeaderServiceImpl extends ServiceImpl<SaleHeaderMapper, SaleHeader> implements SaleHeaderService {
    @Autowired
    private SaleHeaderMapper saleHeaderMapper;

    @Override
    @Transactional
    public DataVO<SaleHeader> find() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(saleHeaderMapper.selectCount(null));
        QueryWrapper<SaleHeader> saleHeaderQueryWrapper = new QueryWrapper<>();
        saleHeaderQueryWrapper.select(SaleHeader.class, info ->!info.getColumn().equals("customer_id") && !info.getColumn().equals("state"));//查询指定某字段以外的数据
        List<SaleHeader> saleHeaderList = saleHeaderMapper.selectList(saleHeaderQueryWrapper);
        dataVO.setData(saleHeaderList);
        return dataVO;
    }

    @Override
    @Transactional
    public DataVO<SaleHeader> findQuery() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(saleHeaderMapper.selectCount(null));
        QueryWrapper<SaleHeader> saleHeaderQueryWrapper = new QueryWrapper<>();
        saleHeaderQueryWrapper.select(SaleHeader.class, info ->!info.getColumn().equals("customer_id"));//查询指定某字段以外的数据
        List<SaleHeader> saleHeaderList = saleHeaderMapper.selectList(saleHeaderQueryWrapper);
        dataVO.setData(saleHeaderList);
        return dataVO;
    }

    @Override
    @Transactional
    public Integer getCustomerId(Integer number) {
        SaleHeader saleHeader = saleHeaderMapper.selectById(number);
        Integer CustomerId = saleHeader.getCustomerId();
        return CustomerId;
    }

    @Override
    @Transactional
    public String getNumberView(Integer number) {
        SaleHeader saleHeader = saleHeaderMapper.selectById(number);
        String numberView = saleHeader.getNumberView();
        return numberView;
    }
}
