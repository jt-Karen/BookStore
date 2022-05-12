package com.bookstore.service.impl.output;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.output.OutHeader;
import com.bookstore.mapper.output.OutHeaderMapper;
import com.bookstore.service.output.OutHeaderService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutHeaderServiceImpl extends ServiceImpl<OutHeaderMapper, OutHeader> implements OutHeaderService {
    @Autowired
    private OutHeaderMapper outHeaderMapper;

    @Override
    @Transactional
    public DataVO<OutHeader> find() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(outHeaderMapper.selectCount(null));
        List<OutHeader> outHeaderList = outHeaderMapper.selectList(null);
        dataVO.setData(outHeaderList);
        return dataVO;
    }
}
