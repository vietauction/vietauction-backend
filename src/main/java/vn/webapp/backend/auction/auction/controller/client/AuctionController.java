package vn.webapp.backend.auction.auction.controller.client;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.auction.domain.enums.AuctionState;
import vn.webapp.backend.auction.auction.dto.response.AuctionCatalogItemResponse;
import vn.webapp.backend.auction.auction.service.AuctionService;
import vn.webapp.backend.auction.shared.constant.ApiEndpoint;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;
import vn.webapp.backend.auction.shared.dto.response.PageMeta;

@RestController
@RequestMapping(ApiEndpoint.Auctions.BASE)
@RequiredArgsConstructor
public class AuctionController {

	private final AuctionService auctionService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<AuctionCatalogItemResponse>>> getAuctions(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int limit,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) AuctionState state,
			@RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endDate,
			@RequestParam(defaultValue = "newest") String sort) {
		Page<AuctionCatalogItemResponse> catalog = auctionService.getPublicCatalog(
				page, limit, keyword, state, categoryId, startDate, endDate, sort);
		PageMeta meta = PageMeta.builder()
				.page(catalog.getNumber() + 1)
				.size(catalog.getSize())
				.totalElements(catalog.getTotalElements())
				.totalPages(catalog.getTotalPages())
				.last(catalog.isLast())
				.build();

		return ResponseEntity.ok(ApiResponse.<List<AuctionCatalogItemResponse>>builder()
				.success(true)
				.message("Lấy danh sách phiên đấu giá thành công")
				.data(catalog.getContent())
				.meta(meta)
				.build());
	}

	@GetMapping(ApiEndpoint.Auctions.ID)
	public ResponseEntity<ApiResponse<AuctionCatalogItemResponse>> getAuctionById(@PathVariable Integer id) {
		return ResponseEntity.ok(ApiResponse.success(
				"Lấy chi tiết phiên đấu giá thành công", auctionService.getPublicAuctionById(id)));
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
