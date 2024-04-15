package com.example.springquiz.service;

import com.example.springquiz.dao.StudentDao;
import com.example.springquiz.dto.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao StudentDao;

    @Override
    public List<Student> selectList(){
        //service 부분은 원래 비즈니스 로직 작성
        return StudentDao.selectList(); //List<Dept> 객체 리턴
    }
}
