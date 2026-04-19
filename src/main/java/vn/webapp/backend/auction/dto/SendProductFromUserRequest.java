package vn.webapp.backend.auction.dto;

import vn.webapp.backend.auction.enums.ProductMaterial;

public record SendProductFromUserRequest(
        Integer userId,
        Double buyNowPrice,
        String description,
        ProductMaterial material,
        Double weight,
        String brand,
        String category,
        String name

) {

}
