package com.bookstore.service.impl.pick;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.pick.PickHeader;
import com.bookstore.mapper.pick.PickHeaderMapper;
import com.bookstore.service.pick.PickHeaderService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PickHeaderServiceImpl extends ServiceImpl<PickHeaderMapper, PickHeader> implements PickHeaderService {
    @Autowired
    private PickHeaderMapper pickHeaderMapper;

    @Override
    @Transactional
    public DataVO<PickHeader> find() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(pickHeaderMapper.selectCount(null));
        QueryWrapper<PickHeader> pickHeaderQueryWrapper = new QueryWrapper<>();
        pickHeaderQueryWrapper.select(PickHeader.class, info ->!info.getColumn().equals("customer_id") && !info.getColumn().equals("state_order"));//查询指定某字段以外的数据
        List<PickHeader> pickHeaderList = pickHeaderMapper.selectList(pickHeaderQueryWrapper);
        dataVO.setData(pickHeaderList);
        return dataVO;
    }

    @Override
    @Transactional
    public DataVO<PickHeader> findQuery() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(pickHeaderMapper.selectCount(null));
        QueryWrapper<PickHeader> pickHeaderQueryWrapper = new QueryWrapper<>();
        pickHeaderQueryWrapper.select(PickHeader.class, info ->!info.getColumn().equals("customer_id") && !info.getColumn().equals("state_output"));//查询指定某字段以外的数据
        List<PickHeader> pickHeaderList = pickHeaderMapper.selectList(pickHeaderQueryWrapper);
        dataVO.setData(pickHeaderList);
        return dataVO;
    }

    @Override
    @Transactional
    public Integer getCustomerId(Integer number) {
        PickHeader pickHeader = pickHeaderMapper.selectById(number);
        Integer CustomerId = pickHeader.getCustomerId();
        return CustomerId;
    }

    @Override
    @Transactional
    public String getNumberView(Integer number) {
        PickHeader pickHeader = pickHeaderMapper.selectById(number);
        String numberView = pickHeader.getNumberView();
        return numberView;
    }
}
