package com.bookstore.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_book")
public class Book {
    @TableId
    private String BookId;
    private String BookName;
    private String Author;
    private String Publisher;
    private float Price;
    private Integer Inventory;
}
