package foodieframe.recipe_sharing_platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "UserID is required")
    @Size(min = 3, max = 50, message = "UserID must be between 3 and 50 characters")
    private Long userID;

    @NotBlank(message = "Image is required")
    @Size(min = 6, message = "Image must be at least 6 characters long")
    private String Image;

    @NotBlank(message = "Title is required")
    @Size(min = 6, message = "Title must be at least 6 characters long")
    private String Title;

    @NotBlank(message = "Description is required")
    @Size(min = 6, message = "Description must be at least 6 characters long")
    private String Description;

    @NotBlank(message = "Category is required")
    @Size(min = 6, message = "Category must be at least 6 characters long")
    private String Category;

    @NotBlank(message = "Steps is required")
    @Size(min = 6, message = "Steps must be at least 6 characters long")
    private String Steps;

    @NotBlank(message = "Tags is required")
    @Size(min = 6, message = "Steps must be at least 6 characters long")
    private String Tags;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSteps() {
        return Steps;
    }

    public void setSteps(String steps) {
        Steps = steps;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }
}