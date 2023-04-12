package jwt.demo.seleniumexpress.service.Impl;

import jwt.demo.seleniumexpress.entity.Student;
import jwt.demo.seleniumexpress.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepo.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("user not found"));
        return  student;
    }
}
