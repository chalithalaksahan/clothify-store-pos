package model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "user_credential")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int credId;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private  String  password;
}
