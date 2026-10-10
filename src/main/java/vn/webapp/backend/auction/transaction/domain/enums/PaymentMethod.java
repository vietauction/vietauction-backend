package vn.webapp.backend.auction.transaction.domain.enums;

public enum PaymentMethod {
    PAY_AT_COUNTER("Thanh toán tại quầy"),
    BANKING("Chuyển khoản");

    private String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
