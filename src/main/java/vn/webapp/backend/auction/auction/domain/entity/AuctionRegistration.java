package vn.webapp.backend.auction.auction.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.webapp.backend.auction.auction.domain.enums.AuctionRegistrationState;
import vn.webapp.backend.auction.identity.domain.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "auction_registration")
public class AuctionRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "registration_fee", nullable = false)
    private double registrationFee;

    @Column(name = "kick_reason")
    private String kickReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private AuctionRegistrationState auctionRegistrationState;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;
}
