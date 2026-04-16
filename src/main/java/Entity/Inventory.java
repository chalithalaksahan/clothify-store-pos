package Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    // The actual amount of this specific variant you currently have in the store
    @Column(name = "qty_on_hand", nullable = false)
    private Integer qtyOnHand;

    // Matches 'minQty' from your ProductDTO
    @Column(name = "min_qty")
    private Integer minQty;

    // Matches 'reOrderLvl' from your ProductDTO
    @Column(name = "reorder_level")
    private Integer reorderLevel;

    // Hibernate will automatically stamp this with the exact time
    // every single time the qtyOnHand goes up or down!
    @UpdateTimestamp
    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdate;

    // Bidirectional link back to the Variant (Optional, but highly recommended)
    @OneToOne(mappedBy = "inventory")
    private Variant variant;

    // 1. ADD THIS: This is the exact variable Hibernate is looking for!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id") // The physical foreign key column in the database
    private Location location;
}