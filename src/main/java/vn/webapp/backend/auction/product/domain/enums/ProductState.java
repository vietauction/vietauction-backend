package vn.webapp.backend.auction.product.domain.enums;

public enum ProductState {
    ACTIVE("Đã duyệt"),
    APPROVING("Đang chờ duyệt"),
    HIDDEN("Đã bị ẩn"),
    AUCTION("Có phiên đấu"),
    HANDED_OVER("Đã bàn giao"),
    RETURNED("Đã hoàn trả");

    private String displayName;

    ProductState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

