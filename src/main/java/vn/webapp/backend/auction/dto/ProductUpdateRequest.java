package vn.webapp.backend.auction.dto;

import vn.webapp.backend.auction.enums.ProductMaterial;

import java.sql.Timestamp;

public record ProductUpdateRequest(
        Integer id,
        String name,
        String description,
        String category,
        Double buyNowPrice,
        ProductMaterial material,
        String brand,
        Double weight,
        Timestamp createDate,
        String image,
        String state
) {
}
