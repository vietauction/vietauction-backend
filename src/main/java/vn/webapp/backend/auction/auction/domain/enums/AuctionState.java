package vn.webapp.backend.auction.auction.domain.enums;

public enum AuctionState {
    ONGOING("Đang diễn ra"),
    WAITING("Đang chờ"),
    FINISHED("Đã kết thúc"),
    PAUSED("Tạm dừng"),
    DELETED("Đã xóa");

    private String vietnameseName;

    AuctionState(String vietnameseName) {
        this.vietnameseName = vietnameseName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }
}
