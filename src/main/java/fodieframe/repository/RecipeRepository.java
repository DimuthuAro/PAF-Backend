package fodieframe.repository;

import fodieframe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // Basic CRUD operations are automatically provided by JpaRepository
} 