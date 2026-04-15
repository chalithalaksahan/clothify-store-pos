package Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    // Maps to the "location" bubble in your diagram
    @Column(name = "location", nullable = false)
    private String locationName;

    @Column(name = "city")
    private String city;

    // Optional: If you ever want to get a list of ALL inventory at this specific location
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Inventory> inventoryList;

}
