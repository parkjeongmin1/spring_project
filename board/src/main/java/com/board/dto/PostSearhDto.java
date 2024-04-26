package com.board.dto;

import com.board.constant.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearhDto {
    private String searchDateType;
    private String searchBy;
    private String searchQuery = "";
}
