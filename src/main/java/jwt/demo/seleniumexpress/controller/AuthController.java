package jwt.demo.seleniumexpress.controller;

import jwt.demo.seleniumexpress.dto.JwtRequest;
import jwt.demo.seleniumexpress.dto.StudentDto;
import jwt.demo.seleniumexpress.security.JwtHelper;
import jwt.demo.seleniumexpress.service.StudentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class AuthController {

    private Logger logger= LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private StudentImpl studentImpl;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<StudentDto> registerUser(@RequestBody StudentDto studentDto){
        StudentDto studentDto1 = studentImpl.saveUser(studentDto);
        return new ResponseEntity<>(studentDto1, HttpStatus.OK);
    }

    @PostMapping("/public")
    public ResponseEntity<String> publicUrl(@RequestBody JwtRequest jwtRequest){
        this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Bad creadentical issue.. Login Failed !!");
        }
    }

    @GetMapping("/private")
    public String privateUrl(){

        return "This is private URL";
    }

}
