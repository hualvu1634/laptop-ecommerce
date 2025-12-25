package e_commerce.project.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import e_commerce.project.dto.request.ProductRequest;
import e_commerce.project.model.Product;
import e_commerce.project.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
        @PostMapping //tạo sản phẩm
        public ResponseEntity<Product> addProduct( @Valid @RequestBody ProductRequest productRequest){
            return new ResponseEntity<>(productService.addProduct(productRequest),HttpStatus.CREATED);
        }
        @GetMapping//xem tất cả sản phẩm
        public ResponseEntity<List<Product>> getAll(){
            return ResponseEntity.ok(productService.getAll());
        }

        @GetMapping("{id}")//xem sản phẩm
        public ResponseEntity<Product> getProduct(@PathVariable Long id){
            return ResponseEntity.ok(productService.getProduct(id));
        }
        @PutMapping("{id}")
        public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductRequest productRequest){
                return ResponseEntity.ok(productService.updateProduct(id, productRequest));
        }
        @DeleteMapping("{id}")
        public ResponseEntity<String> delProduct(@PathVariable Long id){
            return new ResponseEntity<>(productService.delProduct(id),HttpStatus.ACCEPTED);
        }
        @GetMapping("/search")
        public ResponseEntity<Product> SearchByName(@RequestParam("name") String name){
            return ResponseEntity.ok(productService.SearchByName(name));
        }
}

