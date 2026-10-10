package vn.webapp.backend.auction.auction.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.webapp.backend.auction.auction.domain.enums.AuctionHistoryState;
import vn.webapp.backend.auction.identity.domain.entity.User;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "auction_history")
public class AuctionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "price_given", nullable = false)
    private double priceGiven;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    @Column(name = "bid_code")
    private String bidCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private AuctionHistoryState state;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;
}
