package com.bookstore.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_publisher")
public class Publisher {
    @TableId
    private String PublisherId;
    private String PublisherName;
    private String Contact;
    private String Tel;
    private String Address;
}
