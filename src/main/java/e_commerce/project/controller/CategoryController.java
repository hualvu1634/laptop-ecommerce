package e_commerce.project.controller;


import e_commerce.project.model.Category;
import e_commerce.project.service.CategoryService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
   
    @PostMapping
    public ResponseEntity<Category> addCategory( @Valid @RequestBody Category request) {
        return ResponseEntity.ok(categoryService.addCategory(request));
    }
    
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    //xóa danh mục
    @DeleteMapping("{id}")
    public ResponseEntity<String> delCategory(@PathVariable Long id){
        categoryService.delCategory(id);
        return ResponseEntity.ok("Xoa danh muc thanh cong");

    }
}