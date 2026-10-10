package vn.webapp.backend.auction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;
import vn.webapp.backend.auction.shared.dto.response.PageMeta;
import java.util.Collections;
import java.util.List;

@RestController
public class ProductCategoryController {

    @GetMapping("/api/v1/product-categories")
    public ResponseEntity<ApiResponse<Object>> action1(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy danh sách danh mục tài sản thành công")
                .data(new Object())
                .build());
    }

    @GetMapping("/api/v1/product-categories/{id}")
    public ResponseEntity<ApiResponse<Object>> action2(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy chi tiết danh mục tài sản thành công")
                .data(new Object())
                .build());
    }

    @PostMapping("/api/v1/product-categories")
    public ResponseEntity<ApiResponse<Object>> action3(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Tạo danh mục tài sản mới thành công")
                .data(new Object())
                .build());
    }

    @DeleteMapping("/api/v1/product-categories/{id}")
    public ResponseEntity<ApiResponse<Object>> action4(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Xóa danh mục tài sản thành công")
                .data(new Object())
                .build());
    }
}
