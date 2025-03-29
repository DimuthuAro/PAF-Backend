package fodieframe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fodieframe.model.Interaction;
import fodieframe.model.InteractionType;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    List<Interaction> findByRecipeId(Long recipeId);
    List<Interaction> findByUserId(Long userId);
    List<Interaction> findByRecipeIdAndType(Long recipeId, InteractionType type);
    List<Interaction> findByUserIdAndType(Long userId, InteractionType type);
    
    @Query("SELECT COUNT(i) FROM Interaction i WHERE i.recipe.id = :recipeId AND i.type = :type")
    long countByRecipeIdAndType(@Param("recipeId") Long recipeId, @Param("type") InteractionType type);
    
    Optional<Interaction> findByUserIdAndRecipeIdAndType(Long userId, Long recipeId, InteractionType type);
    
    void deleteByRecipeId(Long recipeId);
    void deleteByUserId(Long userId);
    void deleteByRecipeIdAndType(Long recipeId, InteractionType type);
    void deleteByUserIdAndType(Long userId, InteractionType type);
} 