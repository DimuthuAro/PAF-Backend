package fodieframe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fodieframe.model.InteractionType;
import fodieframe.model.Recipe;
import fodieframe.repository.InteractionRepository;
import fodieframe.repository.RecipeRepository;

@Service
public class RecipeService {
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    @Autowired
    private InteractionRepository interactionRepository;

    // Create a new recipe
    @Transactional
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    // Get all recipes
    @Transactional(readOnly = true)
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // Get recipe by ID
    @Transactional(readOnly = true)
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    // Update recipe
    @Transactional
    public Recipe updateRecipe(Long id, Recipe recipeDetails) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            Recipe existingRecipe = recipe.get();
            existingRecipe.setTitle(recipeDetails.getTitle());
            existingRecipe.setDescription(recipeDetails.getDescription());
            existingRecipe.setIngredients(recipeDetails.getIngredients());
            existingRecipe.setInstructions(recipeDetails.getInstructions());
            existingRecipe.setCookingTime(recipeDetails.getCookingTime());
            existingRecipe.setDifficulty(recipeDetails.getDifficulty());
            return recipeRepository.save(existingRecipe);
        }
        return null;
    }

    // Delete recipe
    @Transactional
    public boolean deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            return false;
        }
        recipeRepository.deleteById(id);
        return true;
    }

    // Get like count for a recipe
    @Transactional(readOnly = true)
    public long getLikeCount(Long id) {
        return interactionRepository.countByRecipeIdAndType(id, InteractionType.LIKE);
    }
} 