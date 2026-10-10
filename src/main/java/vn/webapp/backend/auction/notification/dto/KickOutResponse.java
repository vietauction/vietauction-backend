package vn.webapp.backend.auction.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KickOutResponse {
    private Integer userId;
    private Double lastPrice;
    private String kickReason;
    private Timestamp endDate;
}
