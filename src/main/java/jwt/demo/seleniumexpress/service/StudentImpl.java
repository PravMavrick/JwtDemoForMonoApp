package jwt.demo.seleniumexpress.service;

import jwt.demo.seleniumexpress.dto.StudentDto;

public interface StudentImpl {
    StudentDto validateLogin(StudentDto studentDto);
    StudentDto saveUser(StudentDto studentDto);
}
