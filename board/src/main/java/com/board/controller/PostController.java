package com.board.controller;

import com.board.dto.PostFormDto;
import com.board.service.PostService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping(value = "/post/write")
    public String postInsert (Model model){
        model.addAttribute("postFormDto", new PostFormDto());
        return "post/write";
    }

    @PostMapping(value = "/post/write/new")
    public String postInsertnew (@Valid PostFormDto postFormDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) return "post/write";

        try {
            postService.savePost(postFormDto);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage","오류가 발생했습니다.");
            return "post/write";
        }
        return "redirect:/";
    }

}

