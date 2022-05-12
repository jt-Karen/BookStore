package com.bookstore.vo;

import lombok.Data;

@Data
public class SaleBookVO {
    private String BookId;
    private String BookName;
    private String Author;
    private String Publisher;
    private float Price;
    private String Purchase;
}
