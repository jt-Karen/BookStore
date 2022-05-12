package com.bookstore.service.impl.inspect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.inspect.InspectHeader;
import com.bookstore.mapper.inspect.InspectHeaderMapper;
import com.bookstore.service.inspect.InspectHeaderService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InspectHeaderServiceImpl extends ServiceImpl<InspectHeaderMapper, InspectHeader> implements InspectHeaderService {
    @Autowired
    private InspectHeaderMapper inspectHeaderMapper;

    @Override
    @Transactional
    public DataVO<InspectHeader> find() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(inspectHeaderMapper.selectCount(null));
        QueryWrapper<InspectHeader> inspectHeaderQueryWrapper = new QueryWrapper<>();
        inspectHeaderQueryWrapper.select(InspectHeader.class, info ->!info.getColumn().equals("publisher_id") && !info.getColumn().equals("state_consult"));//查询指定某字段以外的数据
        List<InspectHeader> inspectHeaderList = inspectHeaderMapper.selectList(inspectHeaderQueryWrapper);
        dataVO.setData(inspectHeaderList);
        return dataVO;
    }

    @Override
    @Transactional
    public DataVO<InspectHeader> findQuery() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(inspectHeaderMapper.selectCount(null));
        QueryWrapper<InspectHeader> inspectHeaderQueryWrapper = new QueryWrapper<>();
        inspectHeaderQueryWrapper.select(InspectHeader.class, info ->!info.getColumn().equals("publisher_id") && !info.getColumn().equals("state_input"));//查询指定某字段以外的数据
        List<InspectHeader> inspectHeaderList = inspectHeaderMapper.selectList(inspectHeaderQueryWrapper);
        dataVO.setData(inspectHeaderList);
        return dataVO;
    }

    @Override
    @Transactional
    public String getNumberView(Integer number) {
        InspectHeader inspectHeader = inspectHeaderMapper.selectById(number);
        String numberView = inspectHeader.getNumberView();
        return numberView;
    }
}
