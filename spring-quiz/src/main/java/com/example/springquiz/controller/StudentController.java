package com.example.springquiz.controller;


import com.example.springquiz.dto.Student;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springquiz.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    StudentService deptService; //DeptService deptService = new DeptServiceImpl();

    //@PostMapping //post 방식으로 올떄 해당 메소드를 실행
    @GetMapping("/main") //get방식으로 요청이 올때 main() 메소드를 실행
    public List<Student> main() {
        List<Student> list = deptService.selectList();
        return list; //리턴해 주는 값을 Response body에 담아서 view에 전달
    }
}
