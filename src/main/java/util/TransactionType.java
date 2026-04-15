package util;

public enum TransactionType {
    NEW_STOCK,     // Stock arrived from the supplier (+)
    SALE,          // Item sold to a customer (-)
    RETURN,        // Customer returned an item (+)
    DAMAGE,        // Item was damaged and thrown away (-)
    ADJUSTMENT     // Manual correction (e.g., fixing a miscount) (+ or -)
}
