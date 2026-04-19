package vn.webapp.backend.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTransactionResponse  {
    Double totalPriceProductWonByUsername;
    Integer numberTransactionsRequest;
    Integer totalProductWon;
    Integer totalBid;
}
