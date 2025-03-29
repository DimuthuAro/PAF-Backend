package fodieframe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fodieframe.model.Interaction;
import fodieframe.model.InteractionType;
import fodieframe.model.Recipe;
import fodieframe.model.User;
import fodieframe.repository.InteractionRepository;
import fodieframe.repository.RecipeRepository;
import fodieframe.repository.UserRepository;

@Service
public class InteractionService {

    @Autowired
    private InteractionRepository interactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    // Create a new interaction
    @Transactional
    public Interaction createInteraction(Long userId, Long recipeId, InteractionType type, String content) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);

        if (userOpt.isEmpty() || recipeOpt.isEmpty()) {
            return null;
        }

        User user = userOpt.get();
        Recipe recipe = recipeOpt.get();

        // For comments, content should not be null or empty
        if (type == InteractionType.COMMENT && (content == null || content.trim().isEmpty())) {
            return null;
        }

        // Check if interaction already exists
        Optional<Interaction> existingInteraction = interactionRepository
                .findByUserIdAndRecipeIdAndType(userId, recipeId, type);

        if (existingInteraction.isPresent()) {
            // For comments, allow multiple comments
            if (type == InteractionType.COMMENT) {
                Interaction interaction = new Interaction();
                interaction.setUser(user);
                interaction.setRecipe(recipe);
                interaction.setType(type);
                interaction.setContent(content);
                return interactionRepository.save(interaction);
            }
            return null; // Other interaction types should be unique
        }

        Interaction interaction = new Interaction();
        interaction.setUser(user);
        interaction.setRecipe(recipe);
        interaction.setType(type);
        interaction.setContent(content);
        return interactionRepository.save(interaction);
    }

    // Get all interactions for a recipe
    @Transactional(readOnly = true)
    public List<Interaction> getInteractionsByRecipe(Long recipeId) {
        return interactionRepository.findByRecipeId(recipeId);
    }

    // Get all interactions for a user
    @Transactional(readOnly = true)
    public List<Interaction> getInteractionsByUser(Long userId) {
        return interactionRepository.findByUserId(userId);
    }

    // Get interactions by type for a recipe
    @Transactional(readOnly = true)
    public List<Interaction> getInteractionsByRecipeAndType(Long recipeId, InteractionType type) {
        return interactionRepository.findByRecipeIdAndType(recipeId, type);
    }

    // Get interactions by type for a user
    @Transactional(readOnly = true)
    public List<Interaction> getInteractionsByUserAndType(Long userId, InteractionType type) {
        return interactionRepository.findByUserIdAndType(userId, type);
    }

    // Get interaction count by type for a recipe
    @Transactional(readOnly = true)
    public long getInteractionCountByRecipeAndType(Long recipeId, InteractionType type) {
        return interactionRepository.countByRecipeIdAndType(recipeId, type);
    }

    // Update interaction (mainly for comments)
    @Transactional
    public Interaction updateInteraction(Long id, String content) {
        if (content == null || content.trim().isEmpty()) {
            return null;
        }
        
        Optional<Interaction> interactionOpt = interactionRepository.findById(id);
        if (interactionOpt.isEmpty()) {
            return null;
        }
        
        Interaction interaction = interactionOpt.get();
        if (interaction.getType() != InteractionType.COMMENT) {
            return null;
        }
        
        interaction.setContent(content);
        return interactionRepository.save(interaction);
    }

    // Delete interaction
    @Transactional
    public boolean deleteInteraction(Long id) {
        if (!interactionRepository.existsById(id)) {
            return false;
        }
        interactionRepository.deleteById(id);
        return true;
    }

    // Delete all interactions for a recipe
    @Transactional
    public void deleteInteractionsByRecipe(Long recipeId) {
        interactionRepository.deleteByRecipeId(recipeId);
    }

    // Delete all interactions for a user
    @Transactional
    public void deleteInteractionsByUser(Long userId) {
        interactionRepository.deleteByUserId(userId);
    }

    // Delete interactions by type for a recipe
    @Transactional
    public void deleteInteractionsByRecipeAndType(Long recipeId, InteractionType type) {
        interactionRepository.deleteByRecipeIdAndType(recipeId, type);
    }

    // Delete interactions by type for a user
    @Transactional
    public void deleteInteractionsByUserAndType(Long userId, InteractionType type) {
        interactionRepository.deleteByUserIdAndType(userId, type);
    }

    // Check if user has interacted with a recipe in a specific way
    @Transactional(readOnly = true)
    public boolean hasUserInteracted(Long userId, Long recipeId, InteractionType type) {
        return interactionRepository.findByUserIdAndRecipeIdAndType(userId, recipeId, type).isPresent();
    }
} 