package vn.webapp.backend.auction.dto;


public record DashBoardRequest (
        Integer yearGetRegisterAccount,
        Integer yearGetAuction,
        Integer yearGetRevenue,
        Integer yearGetAuctionFailedAndSuccess,
        Integer monthGetAuctionFailedAndSuccess,
        Integer yearGetProduct,
        Integer monthGetProduct,
        Integer yearGetUserJoinAuction
) {
}
