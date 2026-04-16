package Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @Column(name = "supplier_id")
    private String supplierId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "email")
    private String email;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_no")
    private int contactNo;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "is_active")
    private boolean active;

}
