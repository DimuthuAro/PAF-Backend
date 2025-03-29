package fodieframe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fodieframe.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByRecipeId(Long recipeId);
    List<Comment> findByUserId(Long userId);
    void deleteByRecipeId(Long recipeId);
    void deleteByUserId(Long userId);
} 