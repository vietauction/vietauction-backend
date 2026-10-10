package vn.webapp.backend.auction.product.dto.response;

import lombok.Data;
import vn.webapp.backend.auction.product.domain.enums.ProductState;
import vn.webapp.backend.auction.product.domain.enums.ProductMaterial;
import java.sql.Timestamp;
import java.util.List;

@Data
public class ProductResponse {
    private Integer id;
    private String name;
    private Double buyNowPrice;
    private String description;
    private ProductMaterial material;
    private String brand;
    private Double weight;
    private Boolean isHolding;
    private Timestamp receivedDate;
    private Timestamp deliveryDate;
    private Timestamp createDate;
    private ProductState state;
    private Integer userId;
    private Integer categoryId;
    private List<ImageResponse> images;
}
