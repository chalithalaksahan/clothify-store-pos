import dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import service.custom.ProductService;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class ProductTest {

    ProductService productService;


    @Test // This tells Java: "This is a test robot!"
    public void testSkuValidation() {
        // 1. Arrange
        String sku = "SKU-123";

        // 2. Act
        boolean isValid = sku.startsWith("SKU-");

        // 3. Assert (The most important part!)
        assertTrue(isValid, "The SKU should start with SKU-");
    }

    @Test
    void should_fail_when_selling_price_is_lower_than_cost() {
        ProductDTO badProduct = new ProductDTO();
        badProduct.setCostPrice("1500.00");
        badProduct.setSellingPrice("1200.00"); // Error! 300 loss.

        // We expect an Exception to be thrown
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(badProduct);
        });

        assertEquals("Selling price must provide a profit!", exception.getMessage());
    }

    @Test
    void should_fail_when_name_is_empty() {
        ProductDTO noNameProduct = new ProductDTO();
        noNameProduct.setName("");

        assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(noNameProduct);
        });
    }

    @Test
    void should_not_allow_duplicate_sku() throws SQLException {
        // 1. Arrange: Save one product
        ProductDTO p1 = new ProductDTO();
        p1.setSkuCode("PROD-001");
        productService.createProduct(p1);

        // 2. Act & Assert: Try to save another with the same SKU
        ProductDTO p2 = new ProductDTO();
        p2.setSkuCode("PROD-001");

        assertThrows(SQLException.class, () -> {
            productService.createProduct(p2);
        }, "Database should reject duplicate Primary Key");
    }
    @Test
    void search_should_be_case_insensitive() throws SQLException {
        // Arrange: Save a "Blue Shirt"
        // Act: Search for "blue"
        List<ProductDTO> results = productService.searchByAll("blue");

        // Assert: Check if it found the "Blue Shirt"
        assertFalse(results.isEmpty());
        assertTrue(results.getFirst().getName().contains("Blue"));
    }
}

