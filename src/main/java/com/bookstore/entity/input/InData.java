package com.bookstore.entity.input;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("in_data")
public class InData {
    private Integer Number;
    private String BookId;
    private Integer input;
}
