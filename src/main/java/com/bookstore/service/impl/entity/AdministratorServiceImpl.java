package com.bookstore.service.impl.entity;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.entity.Administrator;
import com.bookstore.mapper.entity.AdministratorMapper;
import com.bookstore.service.entity.AdministratorService;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator> implements AdministratorService {
}
