package Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "re_order_lvl")
    private int reorderLevel;

    @Column(name = "supplier_id")
    private String supplierId; // Could be a @ManyToOne to a Supplier entity later

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate;

    // Bidirectional link back to Variant (optional, but helpful for queries)
    @OneToOne(mappedBy = "inventory")
    private Variant variant;

    // Methods to safely add/remove stock
    public void addStock(int amount) {
        this.quantity += amount;
        this.lastUpdate = new Date();
    }

    public void reduceStock(int amount) {
        if (this.quantity >= amount) {
            this.quantity -= amount;
            this.lastUpdate = new Date();
        } else {
            throw new IllegalArgumentException("Insufficient stock for this sale!");
        }
    }
}
