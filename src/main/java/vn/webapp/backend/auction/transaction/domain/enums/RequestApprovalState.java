package vn.webapp.backend.auction.transaction.domain.enums;

public enum RequestApprovalState {
    ACTIVE("Đang hiển thị"),
    HIDDEN("Đã bị ẩn");

    private String vietnameseName;

    RequestApprovalState(String vietnameseName) {
        this.vietnameseName = vietnameseName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }
}
