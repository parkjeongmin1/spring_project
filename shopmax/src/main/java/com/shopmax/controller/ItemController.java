package com.shopmax.controller;


import com.shopmax.dto.ItemFormDto;
import com.shopmax.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    //상품 등록 페이지 보여줌
    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        //valid 체크를 위해 비어있는 DT 객체를 꼭 넣어준다.
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

    //상품 등록 처리(insert)
    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
                          // @RequestParam: input 태그의 name 작성, 이미지를 5개 받아오므로 List 로 받는다
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        if (bindingResult.hasErrors()) return "item/itemForm"; //유효성 체크에서 걸리면

        //상품등록 전에 첫번째 이미지가 있는지 없는지 검사(첫번째 이미지는 필수 입력)
        if (itemImgFileList.get(0).isEmpty()) {
            model.addAttribute("","첫번째 상품 이미지는 필수 입력입니다.");
            return "item/itemForm";
        }
        try{
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "상품등록 중 에러가 발생했습니다.");
            return "item/itemForm";
        }
        return "redirect:/"; //상품등록이 문제없이 완료되면 index 화면으로 이동
    }
}