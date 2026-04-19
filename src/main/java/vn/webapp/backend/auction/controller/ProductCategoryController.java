package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.model.ProductCategory;
import vn.webapp.backend.auction.service.category.ProductCategoryService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-category")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductCategory>> getAllProductCategories() {
        return ResponseEntity.ok(productCategoryService.getAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(productCategoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProductCategory> saveProductCategory(@RequestBody ProductCategory productCategory) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productCategoryService.saveProductCategory(productCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductCategory> deleteProductCategory(@PathVariable Integer id) {
        productCategoryService.deleteProductCategory(id);
        return ResponseEntity.noContent().build();
    }
}
