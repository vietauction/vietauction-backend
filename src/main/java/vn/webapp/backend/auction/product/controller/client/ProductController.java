package vn.webapp.backend.auction.product.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.product.dto.request.ProductRequest;
import vn.webapp.backend.auction.product.dto.response.ProductResponse;
import vn.webapp.backend.auction.shared.constant.ApiEndpoint;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;
import vn.webapp.backend.auction.shared.dto.response.PageMeta;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.Products.BASE)
public class ProductController {

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String name) {
        
        PageMeta meta = PageMeta.builder().page(page).size(limit).totalElements(0).totalPages(0).last(true).build();
        return ResponseEntity.ok(ApiResponse.<List<ProductResponse>>builder()
                .success(true)
                .message("Lấy danh sách tài sản thành công")
                .data(Collections.emptyList())
                .meta(meta)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.<ProductResponse>builder()
                .success(true)
                .message("Lấy chi tiết tài sản thành công")
                .data(new ProductResponse())
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(ApiResponse.<ProductResponse>builder()
                .success(true)
                .message("Tạo tài sản thành công")
                .data(new ProductResponse())
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(ApiResponse.<ProductResponse>builder()
                .success(true)
                .message("Cập nhật tài sản thành công")
                .data(new ProductResponse())
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Xóa tài sản thành công")
                .data(null)
                .build());
    }
}
