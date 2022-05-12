package com.bookstore.vo;

import lombok.Data;

@Data
public class InBookVO {
    private String BookId;
    private String BookName;
    private String Author;
    private String Publisher;
    private float Price;
    private Integer Input;
}
