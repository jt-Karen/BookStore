package com.bookstore.entity.inspect;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("inspection_data")
public class InspectData {
    private Integer Number;
    private String BookId;
    private Integer Arrival;
    private String Qualified;
}
