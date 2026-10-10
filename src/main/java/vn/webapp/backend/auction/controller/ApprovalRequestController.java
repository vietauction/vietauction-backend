package vn.webapp.backend.auction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;
import vn.webapp.backend.auction.shared.dto.response.PageMeta;
import java.util.Collections;
import java.util.List;

@RestController
public class ApprovalRequestController {

    @GetMapping("/api/v1/approval-requests/{id}")
    public ResponseEntity<ApiResponse<Object>> action1(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy chi tiết yêu cầu phê duyệt thành công")
                .data(new Object())
                .build());
    }

    @PutMapping("/api/v1/approval-requests/{id}/state")
    public ResponseEntity<ApiResponse<Object>> action2(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Cập nhật trạng thái thành công")
                .data(new Object())
                .build());
    }



    @GetMapping("/api/v1/approval-requests")
    public ResponseEntity<ApiResponse<Object>> action5(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy request theo role of sender thành công")
                .data(new Object())
                .build());
    }

    @GetMapping("/api/v1/approval-requests/{id}/state")
    public ResponseEntity<ApiResponse<Object>> action6(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy request need member confirm thành công")
                .data(new Object())
                .build());
    }

    @PostMapping("/api/v1/users/{userId}/approval-requests")
    public ResponseEntity<ApiResponse<Object>> action7(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("New request tài sản from người dùng thành công")
                .data(new Object())
                .build());
    }

    @PostMapping("/api/v1/approval-requests")
    public ResponseEntity<ApiResponse<Object>> action8(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("New request tài sản from staff thành công")
                .data(new Object())
                .build());
    }


    @GetMapping("/api/v1/users/{userId}/approval-requests")
    public ResponseEntity<ApiResponse<Object>> action10(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy yêu cầu phê duyệt theo người dùng ID thành công")
                .data(new Object())
                .build());
    }

}
