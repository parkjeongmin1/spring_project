package com.board.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message = "필수 입력입니다.")
    private String name;

    @NotEmpty(message = "필수 입력입니다.")
    @Email(message = "올바른 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "필수 입력입니다.")
    @Length(min = 8, max = 16, message = "비밀번호는 8~16자 사이 입력해주세요")
    private String password;

}
