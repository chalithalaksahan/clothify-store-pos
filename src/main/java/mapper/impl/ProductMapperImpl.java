package mapper.impl;

import Entity.Category;
import Entity.Inventory;
import Entity.Product;
import Entity.Variant;
import dto.CategoryDTO;
import dto.ProductDTO;
import mapper.ProductMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductMapperImpl implements ProductMapper {
    @Override
    public Product toProductEntity(ProductDTO dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setVariants(new ArrayList<>());

        // Map the direct entity relationship
        product.setSupplier(dto.getSupplier());

        // Convert the CategoryDTO into a Category Entity for Hibernate
        if (dto.getCategory() != null) {
            Category category = new Category();
            category.setCategoryCode(dto.getCategory().getCategoryCode());
            category.setCategoryName(dto.getCategory().getCategoryName());
            product.setCategory(category);
        }

        return product;
    }

    @Override
    public Variant toVariantEntity(ProductDTO dto, Product parentProduct) {
        if (dto == null) return null;

        Variant variant = new Variant();
        variant.setProduct(parentProduct); // Link to parent
        variant.setSku(dto.getSkuCode());
        variant.setColor(dto.getColor());
        variant.setSize(dto.getSize());

        // Safely parse String money values into BigDecimal
        try {
            if (dto.getCostPrice() != null && !dto.getCostPrice().isEmpty()) {
                variant.setUnitCost(new BigDecimal(dto.getCostPrice()));
            }
            if (dto.getSellingPrice() != null && !dto.getSellingPrice().isEmpty()) {
                variant.setRetailPrice(new BigDecimal(dto.getSellingPrice()));
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid price format for SKU: " + dto.getSkuCode());
        }

        return variant;
    }

    @Override
    public Inventory toInventoryEntity(ProductDTO dto, Variant parentVariant) {
        if (dto == null) return null;

        Inventory inventory = new Inventory();
        inventory.setVariant(parentVariant); // Link to parent

        // A brand new product always starts with 0 physically on the shelf!
        inventory.setQtyOnHand(dto.getQtyOnHand() != null && !dto.getQtyOnHand().isEmpty() ? Integer.parseInt(dto.getQtyOnHand()) : 0);

        // Safely parse String quantities into Integers
        try {
            if (dto.getMinQty() != null && !dto.getMinQty().isEmpty()) {
                inventory.setMinQty(Integer.parseInt(dto.getMinQty()));
            }
            if (dto.getReOrderLvl() != null && !dto.getReOrderLvl().isEmpty()) {
                inventory.setReorderLevel(Integer.parseInt(dto.getReOrderLvl()));
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid inventory quantity format for SKU: " + dto.getSkuCode());
        }

        return inventory;
    }

    @Override
    public ProductDTO toDto(Product product, Variant variant, Inventory inventory) {
        if (product == null || variant == null) return null;

        ProductDTO dto = new ProductDTO();

        // From Product
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setSupplier(product.getSupplier());

        if(product.getCategory()!= null){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategoryCode(product.getCategory().getCategoryCode());
            categoryDTO.setCategoryName(product.getCategory().getCategoryName());
            dto.setCategory(categoryDTO);
        }
        // From Variant
        dto.setSkuCode(variant.getSku());
        dto.setColor(variant.getColor());
        dto.setSize(variant.getSize());

        if (variant.getUnitCost() != null) dto.setCostPrice(variant.getUnitCost().toString());
        if (variant.getRetailPrice() != null) dto.setSellingPrice(variant.getRetailPrice().toString());

        // From Inventory
        if (inventory != null) {
            if(inventory.getQtyOnHand() != null) dto.setQtyOnHand(inventory.getQtyOnHand().toString());
            if (inventory.getMinQty() != null) dto.setMinQty(inventory.getMinQty().toString());
            if (inventory.getReorderLevel() != null) dto.setReOrderLvl(inventory.getReorderLevel().toString());
        }

        return dto;
    }

    @Override
    public List<ProductDTO> toDtoList(List<Product> all) {
        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product product : all) {
            for (Variant variant : product.getVariants()) {
                Inventory inventory = variant.getInventory();
                ProductDTO dto = toDto(product, variant, inventory);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    @Override
    public ProductDTO toDto(Product product) {

        if (product == null) return null;

        ProductDTO dto = new ProductDTO();
        product.getVariants().forEach(variant -> {
                    Inventory inventory = variant.getInventory();
                    ProductDTO variantDto = toDto(product, variant, inventory);
                    dto.setName(variantDto.getName());
                    dto.setDescription(variantDto.getDescription());
                    dto.setSupplier(variantDto.getSupplier());
                    dto.setCategory(variantDto.getCategory());
                    dto.setSkuCode(variantDto.getSkuCode());
                    dto.setColor(variantDto.getColor());
                    dto.setSize(variantDto.getSize());
                    dto.setCostPrice(variantDto.getCostPrice());
                    dto.setSellingPrice(variantDto.getSellingPrice());
                    dto.setQtyOnHand(variantDto.getQtyOnHand());
                    dto.setMinQty(variantDto.getMinQty());
                    dto.setReOrderLvl(variantDto.getReOrderLvl());
                });

        return dto;
    }


}
