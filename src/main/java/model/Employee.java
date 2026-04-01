package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "name")
    private String name;

    @Column(name = "contact")
    private int contact;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "confirm_password")
    private String ConfirmPassword;

    @Column(name = "salary")
    private double salary;

    @Column(name = "user_role")
    private String userRole;

    @Column(name = "hire_date")
    private String hireDate;

    @Column(name = "is_active")
    private boolean isActive;
}
