package foodieframe.recipe_sharing_platform.repository;

import foodieframe.recipe_sharing_platform.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Spring Data JPA will automatically implement CRUD methods
}