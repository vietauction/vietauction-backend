package vn.webapp.backend.auction.product.dto.request;

import lombok.Data;
import vn.webapp.backend.auction.product.domain.enums.ProductMaterial;

@Data
public class ProductRequest {
    private String name;
    private Double buyNowPrice;
    private String description;
    private ProductMaterial material;
    private String brand;
    private Double weight;
    private Integer categoryId;
}
