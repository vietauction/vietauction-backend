package vn.webapp.backend.auction.product.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.webapp.backend.auction.product.domain.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
