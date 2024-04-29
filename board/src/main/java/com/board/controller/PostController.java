package com.board.controller;

import com.board.dto.PostFormDto;
import com.board.dto.PostSearhDto;
import com.board.entity.Post;
import com.board.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller //사용자의 요청을 처리하고 뷰에 객체를 넘겨주는 역할
@RequiredArgsConstructor //의존성주입을 임의의 코드없이 자동으로 설정해주는 역할
public class PostController {
    private final PostService postService; //postService 클래스를 가져와서 사용한다.

    //작성페이지
    @GetMapping(value = "/post/write") //view 로 화면에 보여주는 역할
    public String postInsert (Model model){ //model: MVC에서 사용되는 인터페이스로, 컨트롤러에서 뷰로 데이터를 전달하는데 사용됨
        model.addAttribute("postFormDto", new PostFormDto()); //postFormDto 라는 이름의 속성을 모델에 추가함 , 값으로 새로운 postFormDto 객체를 할당한다
        //이렇게 함으로 뷰에서 이 객체에 접근하여 화면에 데이터를 표시할수 있다.
        return "post/write"; // 해당 경로로 파일을 찾아서 클라이언트에 반환하여 뷰로 보여준다.
    }

    //작성
    @PostMapping(value = "/post/write/new") // 어떠한 기능을 동작했을때 사용되도록 하는 역할
    public String postInsertNew (@Valid PostFormDto postFormDto, BindingResult bindingResult, Model model){
        // @Valid: 유효성 검사를 요청 , BindingResult: 검증 결과를 저장, model: 뷰로 데이터를 전달하는데 사용
        if (bindingResult.hasErrors()) return "post/write"; //유효성검사 오류가 나면 해당 경로로 리턴

        try {
            postService.savePost(postFormDto);
            //postService 를 사용하고 postFormDto 를 이용하여 포스트를 저장
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage","오류가 발생했습니다.");
            return "post/write";
            //포스트 저장중에 예외가 발생하면 예외를 처리하고 에러 메시지를 설정한 후 다시 해당 경로로 이동.
        }
        return "redirect:/post/list"; // 포스트작성이 성공적으로 처리되었을때 홈페이지로 이동
    }


    //수정페이지
    @GetMapping(value = "/post/rewrite/{postId}")
    public String postDtl(@PathVariable("postId") Long postId, Model model) {

        try {
            PostFormDto postFormDto = postService.getPostDtl(postId);
            model.addAttribute("postFormDto", postFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "에러가 발생했습니다.");
            //에러발생시 비어있는 객체를 넘겨준다.
            model.addAttribute("postFormDto", new PostFormDto());
            return "post/rewrite";
        }


        return "post/rewrite";
    }

    //수정
    @PostMapping(value = "/post/rewrite/{postId}")
    public String postUpdate(@Valid PostFormDto postFormDto, BindingResult bindingResult, Model model,
                             @PathVariable("postId") Long postId){
        if (bindingResult.hasErrors()) return "post/list";

        PostFormDto getPostFormDto = postService.getPostDtl(postId);

        try {
            postService.updatePost(postFormDto);
        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "수정 중에 에러가 발생했습니다.");
            model.addAttribute("postFormDto", getPostFormDto);
            return "post/postDtl";
        }
        return "redirect:/post/list";
    }

    //목록
    @GetMapping(value = {"/post/list", "/post/list/{page}"})
    public String postManage (PostSearhDto postSearhDto,
                              @PathVariable("page")Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Page<Post> posts = postService.getAdminPostPage(postSearhDto, pageable);

        model.addAttribute("posts", posts);
        model.addAttribute("postSearchDto", postSearhDto);
        model.addAttribute("maxPage", 5);

        return "post/list";
    }

    //상세페이지
    @GetMapping(value = "/post/detail/{postId}")
    public String postDtl(Model model, @PathVariable("postId") Long postId){
        PostFormDto postFormDto = postService.getPostDtl(postId);
        model.addAttribute("post",postFormDto);
        return "post/postDtl";
    }


}

