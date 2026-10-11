package vn.webapp.backend.auction.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.product.domain.entity.ProductCategory;
import vn.webapp.backend.auction.product.domain.repository.ProductCategoryRepository;
import vn.webapp.backend.auction.product.dto.response.ProductCategoryResponse;
import vn.webapp.backend.auction.product.service.ProductCategoryService;
import vn.webapp.backend.auction.shared.exception.AppException;
import vn.webapp.backend.auction.shared.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

	private final ProductCategoryRepository productCategoryRepository;

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategoryResponse> getPublicCategories() {
		return productCategoryRepository.findAll().stream()
				.map(this::toResponse)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public ProductCategoryResponse getPublicCategoryById(Integer id) {
		ProductCategory category = productCategoryRepository.findById(id)
				.orElseThrow(() -> new AppException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));

		return toResponse(category);
	}

	private ProductCategoryResponse toResponse(ProductCategory category) {
		ProductCategoryResponse response = new ProductCategoryResponse();
		response.setId(category.getId());
		response.setName(category.getName());
		return response;
	}
}
