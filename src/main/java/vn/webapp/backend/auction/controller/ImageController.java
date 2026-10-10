package vn.webapp.backend.auction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;
import vn.webapp.backend.auction.shared.dto.response.PageMeta;
import java.util.Collections;
import java.util.List;

@RestController
public class ImageController {

    @GetMapping("/api/v1/products/{productId}/images")
    public ResponseEntity<ApiResponse<Object>> action1(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy theo tài sản thành công")
                .data(new Object())
                .build());
    }

    @GetMapping("/api/v1/products/{productId}/images/icon")
    public ResponseEntity<ApiResponse<Object>> action2(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy icon theo tài sản thành công")
                .data(new Object())
                .build());
    }

    @PostMapping("/api/v1/products/{productId}/images")
    public ResponseEntity<ApiResponse<Object>> action3(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Tạo hình ảnh thành công")
                .data(new Object())
                .build());
    }

    @DeleteMapping("/api/v1/products/{productId}/images")
    public ResponseEntity<ApiResponse<Object>> action4(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Xóa hình ảnh theo tài sản ID thành công")
                .data(new Object())
                .build());
    }
}
