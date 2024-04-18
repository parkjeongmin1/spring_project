package com.example.project.controller;
import com.example.project.dto.Post;
import com.example.project.service.PostService;
import com.example.project.util.PagingUtil;
import com.example.project.util.PhotoUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    PagingUtil pagingUtil;

    @Autowired
    PhotoUtil photoUtil;

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/view") //
    public String view(HttpServletRequest request, Model model) {

        try {
            int postId = Integer.parseInt(request.getParameter("postId"));
            String pageNum = request.getParameter("pageNum");
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");

            if (searchValue != null) {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
            }

            //1. 조회수 늘리기
            postService.updateHitCount(postId);

            //2. 게시물 데이터 가져오기
            Post post = postService.getReadPost(postId);

            //가져온 게시물이 없다면
            if (post == null) return "redirect:/list?pageNum=" + pageNum;

            String param = "pageNum=" + pageNum;

            //검색어가 있다면
            if (searchValue != null && !searchValue.equals("")) {
                param += "$searchKey=" + searchKey;
                param += "$searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
            }

            model.addAttribute("post", post);
            model.addAttribute("params", param);
            model.addAttribute("pageNum", pageNum);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return "post/view";
    }


    // localhost/list
    //같은 경로로 get 방식과  post 방식을 동시에 받을 수 있다.
    @RequestMapping(value = "/list",
            method = {RequestMethod.GET, RequestMethod.POST})
    public String list(HttpSession session, HttpServletRequest request, Model model) {
        try {
            String pageNum = request.getParameter("pageNum");
            pagingUtil.setCurrentPage(1); //페이지 번호 항상 1로 우선 초기화
            //현재 페이지의 값을 바꿔준다.
            if (pageNum != null) pagingUtil.setCurrentPage(Integer.parseInt(pageNum));

            int memberId = (int) session.getAttribute("member_id");
            String searchKey = request.getParameter("searchKey"); //검색키워드
            String searchValue = request.getParameter("searchValue"); //검색어

            if (searchValue == null) {
                //검색어가 없다면
                searchKey = "subject";
                searchValue = "";
            } else {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
            }

            Map map = new HashMap();
            map.put("memberId", memberId);
            map.put("searchKey", searchKey);
            map.put("searchValue", searchValue);

            //1. 전체 게시물의 갯수를 가져온다(페이징 처리시 필요)
            int dataCount = postService.getDataCount(map);

            //2. 페이징 처리를 한다(준비 단계)
            // numPerPage: 페이지당 보여줄 게시물 목록의 갯수
            pagingUtil.resetPaging(dataCount, 15);

            map.put("start", pagingUtil.getStart()); // 1 6 11 16..
            map.put("end", pagingUtil.getEnd()); // 5 10 15 20..

            //3. 페이징 처리할 리스트를 가지고 온다.
            List<Post> lists = postService.getPostList(map);

            //4. 검색어에 대한 쿼리스트링을 만든다.
            String param = "";
            String listUrl = "/list";
            String articleUrl = "/view?pageNum=" + pagingUtil.getCurrentPage();

            //검색어가 있다면
            if (searchValue != null && !searchValue.equals("")) {
                param = "searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
            }


            //검색어가 있다면
            if (!param.equals("")) {

                listUrl += "?" + param;

                articleUrl += "&" + param;
            }

            String pageIndexList = pagingUtil.pageIndexList(listUrl);

            model.addAttribute("lists", lists);
            model.addAttribute("articleUrl", articleUrl);
            model.addAttribute("pageIndexList", pageIndexList);
            model.addAttribute("dataCount", dataCount);
            model.addAttribute("searchKey", searchKey);
            model.addAttribute("searchValue", searchValue);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "post/list";
    }


    @GetMapping(value = "/write")
    public String write() {
        return "post/write";
    }

    @PostMapping(value = "/insert")
    public String insertPost(Post post, HttpSession session) {

        try {
            //1. 세션에서 사용자 member_id 가져오기
            Object memberId = session.getAttribute("member_id");

            if (memberId == null) {
                return "redirect:/login"; //세션만료시 로그인 페이지로 이동
            } else {
                post.setMemberId((int) memberId); //insert 하기전 memberId 값 넣어줌
                postService.insertPost(post); //2. 포스트에 insert 해주는 서비스 호출
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/list";
    }

    @GetMapping(value = "/rewrite")
    public String rewrite(HttpServletRequest request, Model model) {
        try {
            int postId = Integer.parseInt(request.getParameter("postId"));
            String pageNum = request.getParameter("pageNum");
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");

            Post post = postService.getReadPost(postId);

            if (post == null) return "redirect:/list?pageNum=" + pageNum;


            String param = "pageNum=" + pageNum;

            if (searchValue != null && !searchValue.equals("")) {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");

                param += "&searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
            }

            model.addAttribute("post", post);
            model.addAttribute("params", param);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("searchKey", searchKey);
            model.addAttribute("searchValue", searchValue);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "post/rewrite";
    }

    @PostMapping(value = "/update")
    public String update(Post post, HttpSession session, HttpServletRequest request) {
        String pageNum = request.getParameter("pageNum");
        String searchKey = request.getParameter("searchKey");
        String searchValue = request.getParameter("searchValue");
        String param = "postId=" + post.getPostId() + "&pageNum=" + pageNum;

        try {
            if (searchValue != null && !searchValue.equals("")) {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
                //검색어가 있다면
                param += "&searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
            }

            Object memberId = session.getAttribute("member_id");

            if (memberId == null) {
                return "redirect:/login";
            } else {
                postService.updatePost(post);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/view?" + param;
    }

    @DeleteMapping(value = "/delete/{postId}")
    public @ResponseBody ResponseEntity deletePost(@PathVariable("postId") int postId,HttpSession session) {
        try {
            Object memberId = session.getAttribute("member_id");

            if(memberId == null) {
                return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.UNAUTHORIZED);
            } else {

            }

            postService.deletePost(postId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("삭제 실패. 관리자에게 문의하세요.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Integer>(postId, HttpStatus.OK);
    }

    @PostMapping(value = "/postImgUpload")
    public String postImgUpload(MultipartHttpServletRequest request, Model model) {

        String uploadPath = photoUtil.ckUpload(request);

        model.addAttribute("uploaded", true);
        model.addAttribute("url", uploadPath);


        return "jsonView";
    }
}




