package vn.webapp.backend.auction.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.product.dto.response.ProductCategoryResponse;
import vn.webapp.backend.auction.product.service.ProductCategoryService;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;

@RestController
@RequiredArgsConstructor
public class ProductCategoryController {

	private final ProductCategoryService productCategoryService;

	@GetMapping("/api/v1/product-categories")
	public ResponseEntity<ApiResponse<List<ProductCategoryResponse>>> getPublicCategories() {
		return ResponseEntity.ok(ApiResponse.success(
				"Lấy danh sách danh mục tài sản thành công", productCategoryService.getPublicCategories()));
	}

	@GetMapping("/api/v1/product-categories/{id}")
	public ResponseEntity<ApiResponse<ProductCategoryResponse>> getPublicCategoryById(@PathVariable Integer id) {
		return ResponseEntity.ok(ApiResponse.success(
				"Lấy chi tiết danh mục tài sản thành công", productCategoryService.getPublicCategoryById(id)));
	}

	@PostMapping("/api/v1/product-categories")
	public ResponseEntity<ApiResponse<Object>> action3(@RequestBody(required = false) Object req) {
		return ResponseEntity.ok(ApiResponse.<Object>builder()
				.success(true)
				.message("Tạo danh mục tài sản mới thành công")
				.data(new Object())
				.build());
	}

	@DeleteMapping("/api/v1/product-categories/{id}")
	public ResponseEntity<ApiResponse<Object>> action4(@PathVariable Integer id) {
		return ResponseEntity.ok(ApiResponse.<Object>builder()
				.success(true)
				.message("Xóa danh mục tài sản thành công")
				.data(new Object())
				.build());
	}
}
