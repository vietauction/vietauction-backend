package vn.webapp.backend.auction.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.ProductCreateRequest;
import vn.webapp.backend.auction.dto.ProductUpdateRequest;
import vn.webapp.backend.auction.dto.SendProductFromUserRequest;
import vn.webapp.backend.auction.enums.ProductState;
import vn.webapp.backend.auction.model.Product;
import vn.webapp.backend.auction.service.product.ProductService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/sorted-and-paged")
    public ResponseEntity<Page<Product>> getAllJewelriesSortedAndPaged(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String username) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);

        Page<Product> jewelries;
        if (username != null) {
            jewelries = productService.getJewelriesByUsername(username, pageable);
        } else {
            jewelries = productService.getAllJewelries(pageable);
        }

        return ResponseEntity.ok(jewelries);
    }

    @GetMapping("/manager-list")
    public ResponseEntity<Page<Product>> getAllJewelriesManager(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String category,
            @RequestParam ProductState state,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        Page<Product> jewelries;
        jewelries = productService.getJewelriesManager(state,productName, category, pageable);
        return ResponseEntity.ok(jewelries);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/get-by-category/{id}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable  Integer id)  {
        return ResponseEntity.ok(productService.getJewelriesByCategoryId(id));
    }

    @GetMapping("/get-by-name/{key}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable  String key)  {
        return ResponseEntity.ok(productService.getJewelriesByNameContain(key));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Product>> getProductByUsername(@PathVariable String username) {
        return ResponseEntity.ok(productService.getProductByUsername(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/in-wait-list")
    public ResponseEntity<Page<Product>> getProductInWaitlist(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(productService.getJewelriesInWaitList(pageable));
    }

    @GetMapping("/is-holding")
    public ResponseEntity<Page<Product>> getProductByHolding(
            @RequestParam ProductState state,
            @RequestParam Boolean isHolding,
            @RequestParam(required = false) String productName,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(productService.getProductByStateAndIsHolding(state,isHolding,category,productName,pageable));
    }

    @GetMapping("/product-passed")
    public ResponseEntity<Page<Product>> getRequestPassed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(productService.getProductPassed(productName, category, pageable));
    }

    @GetMapping("/return-violator")
    public ResponseEntity<Page<Product>> getProductReturnedViolator(
            @RequestParam(required = false) String productName,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(productService.getProductReturnedViolator(category,productName,pageable));
    }

    @GetMapping("/in-handover-list")
    public ResponseEntity<Page<Product>> getProductInHandOver(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(productService.getJewelriesInHandOver(pageable));
    }

    @PostMapping("/product-request")
    public ResponseEntity<Product> requestProduct(@RequestBody SendProductFromUserRequest request) {
        return ResponseEntity.ok(productService.requestProduct(request));
    }

    @GetMapping("/latest")
    public ResponseEntity<Product> lastProduct() {
        return ResponseEntity.ok(productService.getLatestProduct());
    }


    @PutMapping("/set-state-holding/{id}")
    public ResponseEntity<Product> setStateWithHolding(@PathVariable Integer id,  @RequestParam boolean isHolding, @RequestParam ProductState state ) throws MessagingException {
        productService.setStateWithHolding(id,isHolding,state);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    public ResponseEntity<Product> updateById(@RequestBody ProductUpdateRequest product)   {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductCreateRequest product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping("/user-product/{userId}")
    public ResponseEntity<Page<Product>> getJewelriesActiveByUserId(
            @PathVariable  Integer userId,
            @RequestParam(required = false) String productName,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(productService.getJewelriesActiveByUserId(userId, productName,pageable));
    }

}
