package e_commerce.project.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import e_commerce.project.exception.ErrorCode;
import e_commerce.project.exception.ExistedException;
import e_commerce.project.exception.NotFoundException;
import e_commerce.project.model.Category;
import e_commerce.project.model.Product;
import e_commerce.project.repository.CategoryRepository;
import e_commerce.project.repository.ProductRepository;

@Service
@SuppressWarnings("null")
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    public Category addCategory(Category request) {
        
        if (categoryRepository.existsByName(request.getName())) {
            throw new ExistedException(ErrorCode.CATEGORY_EXISTED);
        }
        Category category = new Category();
        category.setName(request.getName());  
        return categoryRepository.save(category);
    }
    
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public List<Product> getCategory(Long categoryId){
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        // 2. Gọi Repository để lấy danh sách
        return productRepository.findByCategoryId(categoryId);
    }
    public void delCategory(Long id){
        if(!categoryRepository.existsById(id)) throw new NotFoundException(ErrorCode.PRODUCT_NOT_IN_CART);
        categoryRepository.deleteById(id);
    }
}