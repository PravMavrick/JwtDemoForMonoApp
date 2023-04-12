package jwt.demo.seleniumexpress.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDto {
    private int studentId;
    private String name;
    private String email;
    private String password;
}
