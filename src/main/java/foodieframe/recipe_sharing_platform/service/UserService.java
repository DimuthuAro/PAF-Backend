package foodieframe.recipe_sharing_platform.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodieframe.recipe_sharing_platform.model.User;
import foodieframe.recipe_sharing_platform.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    // Create
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    // Read all
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Read one
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    // Update
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        
        return userRepository.save(user);
    }
    
    // Delete
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        userRepository.delete(user);
    }
}
