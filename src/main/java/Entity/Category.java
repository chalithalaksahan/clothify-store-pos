package Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "category")
public class Category {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="category_id")
        private Long categoryId;

        @Column(name="category_code")
        private String categoryCode;

        @Column(name="category_name")
        private String categoryName;

        @Column(name="parent_category")
        private String parentCategory;

        @Column(name="status")
        private Boolean status;

        @Column(name="description")
        private String description;
}
