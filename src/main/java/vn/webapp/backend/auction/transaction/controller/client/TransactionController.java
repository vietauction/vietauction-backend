package vn.webapp.backend.auction.transaction.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.shared.constant.ApiEndpoint;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;
import vn.webapp.backend.auction.shared.dto.response.PageMeta;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.Transactions.BASE)
public class TransactionController {

    @GetMapping
    public ResponseEntity<ApiResponse<List<Object>>> getTransactions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Boolean overdue) {
        
        PageMeta meta = PageMeta.builder().page(page).size(limit).totalElements(0).totalPages(0).last(true).build();
        return ResponseEntity.ok(ApiResponse.<List<Object>>builder()
                .success(true)
                .message("Lấy danh sách giao dịch thành công")
                .data(Collections.emptyList())
                .meta(meta)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getTransactionById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy chi tiết giao dịch thành công")
                .data(new Object())
                .build());
    }

    @PutMapping("/{id}/state")
    public ResponseEntity<ApiResponse<Object>> updateTransactionState(
            @PathVariable Integer id, @RequestBody Object request) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Cập nhật trạng thái giao dịch thành công")
                .data(new Object())
                .build());
    }

    @PutMapping("/{id}/payment-method")
    public ResponseEntity<ApiResponse<Object>> updatePaymentMethod(
            @PathVariable Integer id, @RequestBody Object request) {
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Cập nhật phương thức thanh toán thành công")
                .data(new Object())
                .build());
    }
}
