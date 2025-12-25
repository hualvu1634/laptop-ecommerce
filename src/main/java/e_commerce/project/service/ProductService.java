package e_commerce.project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import e_commerce.project.dto.request.ProductRequest;
import e_commerce.project.exception.ErrorCode;
import e_commerce.project.exception.ExistedException;
import e_commerce.project.exception.NotFoundException;
import e_commerce.project.model.Category;
import e_commerce.project.model.Product;
import e_commerce.project.repository.CategoryRepository;
import e_commerce.project.repository.ProductRepository;

@Service
@SuppressWarnings("null")
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    public Product addProduct(ProductRequest request) {
     
        if (productRepository.existsByName(request.getName())) {
            throw new ExistedException(ErrorCode.PRODUCT_EXISTED);
        }

        
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .category(category) // Gán danh mục
                .build();

        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public Product updateProduct(Long id, ProductRequest request) {
      
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

       
        if (request.getCategoryId() != null) {
            Category newCategory = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
            product.setCategory(newCategory);
        }

        return productRepository.save(product);
    }

    // --- 5. XÓA ---
    public String delProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(id);
        return "Xóa sản phẩm thành công";
    }
    public Product SearchByName(String productName){
        return productRepository.findByName(productName).orElseThrow(() ->  new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}