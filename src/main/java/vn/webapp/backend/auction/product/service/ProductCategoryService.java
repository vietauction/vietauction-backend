package vn.webapp.backend.auction.product.service;

import java.util.List;

import vn.webapp.backend.auction.product.dto.response.ProductCategoryResponse;

public interface ProductCategoryService {
	List<ProductCategoryResponse> getPublicCategories();

	ProductCategoryResponse getPublicCategoryById(Integer id);
}
