package vn.webapp.backend.auction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;
import vn.webapp.backend.auction.shared.dto.response.PageMeta;
import java.util.Collections;
import java.util.List;

@RestController
public class PaymentController {

    @GetMapping("/api/v1/payments/vnpay/checkout")
    public ResponseEntity<ApiResponse<Object>> action1(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Tạo yêu cầu thanh toán VNPay thành công")
                .data(new Object())
                .build());
    }

    @GetMapping("/api/v1/payments/vnpay/callback")
    public ResponseEntity<ApiResponse<Object>> action2(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Xử lý callback thanh toán VNPay thành công")
                .data(new Object())
                .build());
    }
}
