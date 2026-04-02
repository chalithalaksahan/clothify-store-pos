package dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDTO {
    private String employeeId;
    private String firstName;
    private String lastName;
    private int contactNo;
    private String email;
    private String password;
    private String confirmPassword;  // Used only for UI validation
    private double salary;
    private String userRole;         // "Admin" or "Staff"
    private LocalDate hireDate;
    private boolean active;
}