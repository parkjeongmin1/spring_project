package com.example.springbasic.controller;

import com.example.springbasic.dto.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ThymeleafExController {

    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model) {
        //model.addAttribute(키값, value) : view단에 데이터를 넘겨줄때사용
        model.addAttribute("data", "타입리프 예제입니다.");
        model.addAttribute("result", 2024);

        //보여주고 싶은 화면의 경로/파일명 작성( templates 하위 경로 )
        return "templates/thymeleafEx/thymeleafEx01";
    }

    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model) {
        Item itemDto = new Item();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);

        return "templates/thymeleafEx/thymeleafEx02";
    }

    @GetMapping(value = "/ex03")
    public String thymeleafExample03(Model model) {
        List<Item> itemDtoList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Item itemDto = new Item();

            itemDto.setItemDetail("상품 상세 설명" + i);
            itemDto.setItemNm("테스트 상품1" + i);
            itemDto.setPrice(10000 * i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "templates/thymeleafEx/thymeleafEx03";
    }

    @GetMapping(value = "/ex04")
    public String thymeleafExample04(Model model) {
        List<Item> itemDtoList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Item itemDto = new Item();

            itemDto.setItemDetail("상품 상세 설명" + i);
            itemDto.setItemNm("테스트 상품1" + i);
            itemDto.setPrice(10000 * i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "templates/thymeleafEx/thymeleafEx04";
    }

    @GetMapping(value = "/ex05")
    public String thymeleafExample05(Model model) {

        return "templates/thymeleafEx/thymeleafEx05";
    }

    @GetMapping(value = "/ex06")
    public String thymeleafExample06(Model model, @RequestParam(value ="param1") String param1, @RequestParam (value ="param2") String param2) {
        System.out.println("param1: " + param1);
        System.out.println("param2: " + param2);

        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);

        return "templates/thymeleafEx/thymeleafEx06";
    }

    @GetMapping(value = "/ex07")
    public String thymeleafExample07() {

        return "thymeleafEx/thymeleafEx07";
    }

    @GetMapping(value = "/ex08")
    public String thymeleafExample08() {

        return "thymeleafEx/thymeleafEx08";
    }
}
