package fodieframe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fodieframe.model.Comment;
import fodieframe.model.Recipe;
import fodieframe.model.User;
import fodieframe.repository.CommentRepository;
import fodieframe.repository.RecipeRepository;
import fodieframe.repository.UserRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    public Comment createComment(Long userId, Long recipeId, String content) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);

        if (user.isPresent() && recipe.isPresent()) {
            Comment comment = new Comment();
            comment.setUser(user.get());
            comment.setRecipe(recipe.get());
            comment.setContent(content);
            return commentRepository.save(comment);
        }
        return null;
    }

    public List<Comment> getCommentsByRecipe(Long recipeId) {
        return commentRepository.findByRecipeId(recipeId);
    }

    public List<Comment> getCommentsByUser(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment updateComment(Long id, String content) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            Comment existingComment = comment.get();
            existingComment.setContent(content);
            return commentRepository.save(existingComment);
        }
        return null;
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public void deleteCommentsByRecipe(Long recipeId) {
        commentRepository.deleteByRecipeId(recipeId);
    }

    public void deleteCommentsByUser(Long userId) {
        commentRepository.deleteByUserId(userId);
    }
} 