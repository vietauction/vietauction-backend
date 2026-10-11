package vn.webapp.backend.auction.product.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import vn.webapp.backend.auction.product.domain.entity.ProductCategory;
import vn.webapp.backend.auction.product.domain.repository.ProductCategoryRepository;
import vn.webapp.backend.auction.shared.exception.AppException;
import vn.webapp.backend.auction.shared.exception.ErrorCode;

@ExtendWith(MockitoExtension.class)
class ProductCategoryServiceImplTest {

	@Mock
	private ProductCategoryRepository productCategoryRepository;

	@InjectMocks
	private ProductCategoryServiceImpl productCategoryService;

	@Test
	void getPublicCategoriesMapsPersistedCategories() {
		when(productCategoryRepository.findAll()).thenReturn(List.of(
				ProductCategory.builder().id(1).name("Nhẫn").build(),
				ProductCategory.builder().id(2).name("Dây chuyền").build()));

		var response = productCategoryService.getPublicCategories();

		assertThat(response).extracting("name").containsExactly("Nhẫn", "Dây chuyền");
	}

	@Test
	void getPublicCategoryByIdReturnsNotFoundForMissingCategory() {
		when(productCategoryRepository.findById(99)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> productCategoryService.getPublicCategoryById(99))
				.isInstanceOfSatisfying(AppException.class,
						exception -> assertThat(exception.getErrorCode())
								.isEqualTo(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));
	}
}
