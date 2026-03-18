package model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "contact_no")
    private int contactNo;

    @Column(name = "salary")
    private double salary;

    @Column(name = "user_role")
    private int userRole;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name = "is_Active")
    private boolean isActive;

    @OneToOne(mappedBy = "user")
    private UserCredential userCredential;

}
