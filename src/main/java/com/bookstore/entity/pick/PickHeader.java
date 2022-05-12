package com.bookstore.entity.pick;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("pick_header")
public class PickHeader {
    @TableId(type = IdType.AUTO)
    private Integer Number;
    private String NumberView;
    private String RelateNumber;
    private Integer CustomerId;
    private String Account;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp Time;
    private String StateOutput;
    private String StateOrder;
}
