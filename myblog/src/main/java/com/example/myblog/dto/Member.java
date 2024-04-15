package com.example.myblog.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Member {
    private int memberId;
    private String email;
    private String password;
    private String name;
}
