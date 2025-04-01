package foodieframe.recipe_sharing_platform.service;

import foodieframe.recipe_sharing_platform.model.Product;
import foodieframe.recipe_sharing_platform.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    // Create
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    
    // Read all
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    // Read one
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    // Update
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        
        return productRepository.save(product);
    }
    
    // Delete
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        productRepository.delete(product);
    }
}