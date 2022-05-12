package com.bookstore.entity.inspect;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("inspection_header")
public class InspectHeader {
    @TableId(type = IdType.AUTO)
    private Integer Number;
    private String NumberView;
    private String RelateNumber;
    private String PublisherId;
    private String Account;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp Time;
    private String StateInput;
    private String StateConsult;
}
