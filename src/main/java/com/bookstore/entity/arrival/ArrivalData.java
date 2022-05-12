package com.bookstore.entity.arrival;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("arrival_data")
public class ArrivalData {
    private Integer Number;
    private String BookId;
    private String Arrival;
}
