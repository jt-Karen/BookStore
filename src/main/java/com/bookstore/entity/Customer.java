package com.bookstore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_customer")
public class Customer {
    @TableId(type = IdType.AUTO)
    private Integer CustomerId;
    private String Name;
    private String Tel;
    private String Address;
}
