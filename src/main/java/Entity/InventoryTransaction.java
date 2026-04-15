package Entity;

import jakarta.persistence.*;
import util.TransactionType;

import java.util.Date;

@Entity
@Table(name = "inventory_transaction")
public class InventoryTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    // Links back to the specific inventory record (the exact location/variant)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    // Uses the Enum we created above
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    // How much the stock changed (+5, -2, etc.)
    @Column(name = "quantity_changed", nullable = false)
    private int quantityChanged;

    // Exact timestamp of the change
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    // Optional: Notes, or the Invoice ID / GRN number associated with this change
    @Column(name = "reference_note")
    private String referenceNote;
}
