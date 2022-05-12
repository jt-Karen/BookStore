package com.bookstore.vo;

import lombok.Data;

@Data
public class PickBookVO {
    private String BookId;
    private String BookName;
    private String Author;
    private String Publisher;
    private float Price;
    private Integer Inventory;
    private Integer Purchase;
    private Integer Short;
}
