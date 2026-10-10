package vn.webapp.backend.auction.auction.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.shared.constant.ApiEndpoint;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;
import vn.webapp.backend.auction.shared.dto.response.PageMeta;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.Auctions.BASE)
public class AuctionController {

    @GetMapping
    public ResponseEntity<ApiResponse<List<Object>>> getAuctions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        PageMeta meta = PageMeta.builder().page(page).size(limit).totalElements(0).totalPages(0).last(true).build();
        return ResponseEntity.ok(ApiResponse.<List<Object>>builder()
                .success(true)
                .message("Lấy danh sách phiên đấu giá thành công")
                .data(Collections.emptyList())
                .meta(meta)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getAuctionById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy chi tiết phiên đấu giá thành công")
                .data(new Object())
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createAuction(@RequestBody Object request) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Tạo phiên đấu giá thành công")
                .data(new Object())
                .build());
    }

    @PutMapping("/{id}/state")
    public ResponseEntity<ApiResponse<Object>> updateAuctionState(@PathVariable Integer id, @RequestBody Object request) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Cập nhật trạng thái phiên đấu giá thành công")
                .data(new Object())
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAuction(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Xóa phiên đấu giá thành công")
                .data(null)
                .build());
    }
}
