package com.bookstore.entity.pick;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("pick_data")
public class PickData {
    private Integer Number;
    private String BookId;
    private Integer Purchase;
    private Integer Short;
}
