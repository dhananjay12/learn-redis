package com.example.spring.redisson.basics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basics")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> product(@PathVariable("id") int id) {
        Product product = productService.getProduct(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        if (id != product.getId()) {
            return ResponseEntity.badRequest().body("Id not matched");
        }
        productService.updateProduct(id, product);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/clear-all")
    public void clearCache() {
        productService.clearCache();
    }

}
