package vn.webapp.backend.auction.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidResponse {
    private Double lastPrice;
    private Double buyNowPrice;
    private Integer auctionId;
    private Timestamp endDate;
    private Long bonusTime;
    private String username;
}
