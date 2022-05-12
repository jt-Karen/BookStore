package com.bookstore.service.impl.arrival;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.arrival.ArrivalHeader;
import com.bookstore.mapper.arrival.ArrivalHeaderMapper;
import com.bookstore.service.arrival.ArrivalHeaderService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArrivalHeaderServiceImpl extends ServiceImpl<ArrivalHeaderMapper, ArrivalHeader> implements ArrivalHeaderService {
    @Autowired
    private ArrivalHeaderMapper arrivalHeaderMapper;

    @Override
    @Transactional
    public DataVO<ArrivalHeader> find() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(arrivalHeaderMapper.selectCount(null));
        QueryWrapper<ArrivalHeader> arrivalHeaderQueryWrapper = new QueryWrapper<>();
        arrivalHeaderQueryWrapper.select(ArrivalHeader.class, info ->!info.getColumn().equals("publisher_id"));//查询指定某字段以外的数据
        List<ArrivalHeader> arrivalHeaderList = arrivalHeaderMapper.selectList(arrivalHeaderQueryWrapper);
        dataVO.setData(arrivalHeaderList);
        return dataVO;
    }

    @Override
    @Transactional
    public String getPublisherId(Integer number) {
        ArrivalHeader arrivalHeader = arrivalHeaderMapper.selectById(number);
        String PublisherId = arrivalHeader.getPublisherId();
        return PublisherId;
    }

    @Override
    @Transactional
    public String getNumberView(Integer number) {
        ArrivalHeader arrivalHeader = arrivalHeaderMapper.selectById(number);
        String numberView = arrivalHeader.getNumberView();
        return numberView;
    }
}
