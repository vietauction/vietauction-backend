package vn.webapp.backend.auction.product.infrastructure.mapper;

import org.mapstruct.Mapper;
import vn.webapp.backend.auction.product.domain.entity.Product;
import vn.webapp.backend.auction.product.dto.response.ProductResponse;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(Product product);
}
