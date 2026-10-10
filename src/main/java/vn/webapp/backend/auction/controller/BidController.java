package vn.webapp.backend.auction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;
import vn.webapp.backend.auction.shared.dto.response.PageMeta;
import java.util.Collections;
import java.util.List;

@RestController
public class BidController {

    @GetMapping("/api/v1/auctions/{auctionId}/bids")
    public ResponseEntity<ApiResponse<Object>> action1(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy lịch sử trả giá theo phiên đấu giá thành công")
                .data(new Object())
                .build());
    }


    @GetMapping("/api/v1/users/{username}/bids")
    public ResponseEntity<ApiResponse<Object>> action3(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy lịch sử trả giá theo người dùng thành công")
                .data(new Object())
                .build());
    }

    @GetMapping("/api/v1/bids")
    public ResponseEntity<ApiResponse<Object>> action4(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy lịch sử trả giá theo ngày thành công")
                .data(new Object())
                .build());
    }


    @PostMapping("/api/v1/auctions/{auctionId}/bids")
    public ResponseEntity<ApiResponse<Object>> action6(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Tạo lượt trả giá mới thành công")
                .data(new Object())
                .build());
    }

    @DeleteMapping("/api/v1/auctions/{auctionId}/bids/{bidId}")
    public ResponseEntity<ApiResponse<Object>> action7(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Xóa lượt trả giá theo người dùng và phiên đấu giá thành công")
                .data(new Object())
                .build());
    }

    @GetMapping("/api/v1/auctions/{auctionId}/registrations")
    public ResponseEntity<ApiResponse<Object>> action8(@RequestBody(required=false) Object req) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy danh sách đăng ký của phiên đấu giá thành công")
                .data(new Object())
                .build());
    }
}
