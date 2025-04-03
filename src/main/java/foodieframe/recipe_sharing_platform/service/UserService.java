package foodieframe.recipe_sharing_platform.service;

import java.util.List;
import java.util.Optional;

import foodieframe.recipe_sharing_platform.model.AuthResponse;
import foodieframe.recipe_sharing_platform.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodieframe.recipe_sharing_platform.model.User;
import foodieframe.recipe_sharing_platform.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
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

    public boolean authenticateUser(String email, String password) {
        // Implement authentication logic here
        // For example, check if the user exists and the password matches
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
    
    public AuthResponse login(String email, String password) {
        User user = userRepository.findByEmail(email);
        System.out.println("User: " + user);
        System.out.println("Password: " + password);
        if (user != null && user.getPassword().equals(password)) {
            String token = jwtUtil.generateToken(email);
            // Don't include password in response
            user.setPassword(null);
            return new AuthResponse(token, user);
        }
        return null;
    }

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email is already in use.");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters long.");
        }
        return userRepository.save(user);
    }
}
