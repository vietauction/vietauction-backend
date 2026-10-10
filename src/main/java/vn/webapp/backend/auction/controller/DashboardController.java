package vn.webapp.backend.auction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;
import vn.webapp.backend.auction.shared.dto.response.PageMeta;
import java.util.Collections;
import java.util.List;

@RestController
public class DashboardController {

    @GetMapping("/api/v1/dashboard")
    public ResponseEntity<ApiResponse<Object>> action1(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy danh sách dashboard thành công")
                .data(new Object())
                .build());
    }
}
