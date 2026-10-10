package vn.webapp.backend.auction.auction.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;
import vn.webapp.backend.auction.shared.dto.response.PageMeta;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/auctions")
public class AuctionAdminController {

    @GetMapping
    public ResponseEntity<ApiResponse<List<Object>>> getAdminAuctions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String state) {
        
        PageMeta meta = PageMeta.builder().page(page).size(limit).totalElements(0).totalPages(0).last(true).build();
        return ResponseEntity.ok(ApiResponse.<List<Object>>builder()
                .success(true)
                .message("Lấy danh sách phiên đấu giá (Admin) thành công")
                .data(Collections.emptyList())
                .meta(meta)
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
}
