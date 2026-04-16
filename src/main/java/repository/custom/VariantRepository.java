package repository.custom;

import Entity.Variant;
import repository.CrudRepository;

public interface VariantRepository extends CrudRepository<Variant, String> {
    Variant findBySku(String skuCode);
}
