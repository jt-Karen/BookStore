package com.bookstore.entity.sale;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sale_data")
public class SaleData {
    private Integer Number;
    private String BookId;
    private String Purchase;
}
