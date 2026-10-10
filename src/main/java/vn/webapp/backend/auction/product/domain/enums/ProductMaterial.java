package vn.webapp.backend.auction.product.domain.enums;

public enum ProductMaterial {
    GOLD("Vàng"),
    SILVER("Bạc"),
    PLATINUM("Bạch kim"),
    DIAMOND("Kim cương");

    private String displayName;

    ProductMaterial(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
