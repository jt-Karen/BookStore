package com.bookstore.service.impl.input;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.input.InHeader;
import com.bookstore.mapper.input.InHeaderMapper;
import com.bookstore.service.input.InHeaderService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InHeaderServiceImpl extends ServiceImpl<InHeaderMapper, InHeader> implements InHeaderService {
    @Autowired
    private InHeaderMapper inHeaderMapper;

    @Override
    @Transactional
    public DataVO<InHeader> find() {
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(inHeaderMapper.selectCount(null));
        List<InHeader> inHeaderList = inHeaderMapper.selectList(null);
        dataVO.setData(inHeaderList);
        return dataVO;
    }
}
