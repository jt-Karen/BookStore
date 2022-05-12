package com.bookstore.entity.order;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("order_data")
public class OrderData {
    private Integer Number;
    private String BookId;
    private String Orders;
}
