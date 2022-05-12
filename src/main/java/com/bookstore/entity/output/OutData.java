package com.bookstore.entity.output;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("out_data")
public class OutData {
    private Integer Number;
    private String BookId;
    private Integer Output;
}
