package jwt.demo.seleniumexpress.service.Impl;

import jwt.demo.seleniumexpress.dto.StudentDto;
import jwt.demo.seleniumexpress.entity.Student;
import jwt.demo.seleniumexpress.repository.StudentRepo;
import jwt.demo.seleniumexpress.service.StudentImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService implements StudentImpl {


    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public StudentDto validateLogin(StudentDto studentDto) {
        return null;
    }

    @Override
    public StudentDto saveUser(StudentDto studentDto) {

        ModelMapper modelMapper = new ModelMapper();
        Student student = modelMapper.map(studentDto, Student.class);
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        studentRepo.save(student);
        StudentDto studentDto1 = modelMapper.map(student, StudentDto.class);

        return studentDto1;
    }

}
