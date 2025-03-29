package fodieframe.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fodieframe.model.Interaction;
import fodieframe.model.InteractionType;
import fodieframe.service.InteractionService;

@RestController
@RequestMapping("/api/interactions")
@CrossOrigin(origins = "*")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;

    // Create a new interaction
    @PostMapping("/users/{userId}/recipes/{recipeId}")
    public ResponseEntity<?> createInteraction(
            @PathVariable Long userId,
            @PathVariable Long recipeId,
            @RequestParam InteractionType type,
            @RequestBody(required = false) String content) {
        
        // Validate content for comments
        if (type == InteractionType.COMMENT && (content == null || content.trim().isEmpty())) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Content is required for comments"));
        }
        
        Interaction createdInteraction = interactionService.createInteraction(userId, recipeId, type, content);
        
        if (createdInteraction == null) {
            if (interactionService.hasUserInteracted(userId, recipeId, type) && type != InteractionType.COMMENT) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "User has already interacted with this recipe in this way"));
            } else {
                return ResponseEntity.notFound()
                    .build();
            }
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInteraction);
    }

    // Get all interactions for a recipe
    @GetMapping("/recipes/{recipeId}")
    public ResponseEntity<List<Interaction>> getInteractionsByRecipe(@PathVariable Long recipeId) {
        List<Interaction> interactions = interactionService.getInteractionsByRecipe(recipeId);
        return ResponseEntity.ok(interactions);
    }

    // Get all interactions for a user
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Interaction>> getInteractionsByUser(@PathVariable Long userId) {
        List<Interaction> interactions = interactionService.getInteractionsByUser(userId);
        return ResponseEntity.ok(interactions);
    }

    // Get interactions by type for a recipe
    @GetMapping("/recipes/{recipeId}/type/{type}")
    public ResponseEntity<List<Interaction>> getInteractionsByRecipeAndType(
            @PathVariable Long recipeId,
            @PathVariable InteractionType type) {
        List<Interaction> interactions = interactionService.getInteractionsByRecipeAndType(recipeId, type);
        return ResponseEntity.ok(interactions);
    }

    // Get interactions by type for a user
    @GetMapping("/users/{userId}/type/{type}")
    public ResponseEntity<List<Interaction>> getInteractionsByUserAndType(
            @PathVariable Long userId,
            @PathVariable InteractionType type) {
        List<Interaction> interactions = interactionService.getInteractionsByUserAndType(userId, type);
        return ResponseEntity.ok(interactions);
    }

    // Get interaction count by type for a recipe
    @GetMapping("/recipes/{recipeId}/type/{type}/count")
    public ResponseEntity<Map<String, Long>> getInteractionCountByRecipeAndType(
            @PathVariable Long recipeId,
            @PathVariable InteractionType type) {
        long count = interactionService.getInteractionCountByRecipeAndType(recipeId, type);
        return ResponseEntity.ok(Map.of("count", count));
    }

    // Update interaction (mainly for comments)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInteraction(
            @PathVariable Long id,
            @RequestBody String content) {
        
        if (content == null || content.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Content cannot be empty"));
        }
        
        Interaction updatedInteraction = interactionService.updateInteraction(id, content);
        
        if (updatedInteraction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Interaction not found or not a comment"));
        }
        
        return ResponseEntity.ok(updatedInteraction);
    }

    // Delete interaction
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInteraction(@PathVariable Long id) {
        boolean deleted = interactionService.deleteInteraction(id);
        
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.noContent().build();
    }

    // Delete all interactions for a recipe
    @DeleteMapping("/recipes/{recipeId}")
    public ResponseEntity<Void> deleteInteractionsByRecipe(@PathVariable Long recipeId) {
        interactionService.deleteInteractionsByRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }

    // Delete all interactions for a user
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteInteractionsByUser(@PathVariable Long userId) {
        interactionService.deleteInteractionsByUser(userId);
        return ResponseEntity.noContent().build();
    }

    // Delete interactions by type for a recipe
    @DeleteMapping("/recipes/{recipeId}/type/{type}")
    public ResponseEntity<Void> deleteInteractionsByRecipeAndType(
            @PathVariable Long recipeId,
            @PathVariable InteractionType type) {
        interactionService.deleteInteractionsByRecipeAndType(recipeId, type);
        return ResponseEntity.noContent().build();
    }

    // Delete interactions by type for a user
    @DeleteMapping("/users/{userId}/type/{type}")
    public ResponseEntity<Void> deleteInteractionsByUserAndType(
            @PathVariable Long userId,
            @PathVariable InteractionType type) {
        interactionService.deleteInteractionsByUserAndType(userId, type);
        return ResponseEntity.noContent().build();
    }

    // Check if user has interacted with a recipe in a specific way
    @GetMapping("/users/{userId}/recipes/{recipeId}/type/{type}/check")
    public ResponseEntity<Map<String, Boolean>> hasUserInteracted(
            @PathVariable Long userId,
            @PathVariable Long recipeId,
            @PathVariable InteractionType type) {
        boolean hasInteracted = interactionService.hasUserInteracted(userId, recipeId, type);
        return ResponseEntity.ok(Map.of("hasInteracted", hasInteracted));
    }
} 