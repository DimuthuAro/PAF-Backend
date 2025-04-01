package foodieframe.recipe_sharing_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import foodieframe.recipe_sharing_platform.controller.ProductController;

@SpringBootApplication
@RestController
public class RecipeSharingPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeSharingPlatformApplication.class, args);
	}

	private final ProductController productController = new ProductController();

	@GetMapping("/")
	public Object getAllProducts() {
		return productController.getAllProducts();
	}
}

