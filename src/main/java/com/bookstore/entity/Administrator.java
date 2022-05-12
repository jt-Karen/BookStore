package com.bookstore.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_administrator")
public class Administrator {
    @TableId
    private String Account;
    private String Password;
    private String Department;
    private Integer Authority;
}
