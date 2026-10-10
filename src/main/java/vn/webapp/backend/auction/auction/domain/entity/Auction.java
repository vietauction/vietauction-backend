package vn.webapp.backend.auction.auction.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.webapp.backend.auction.auction.domain.enums.AuctionState;
import vn.webapp.backend.auction.identity.domain.entity.User;
import vn.webapp.backend.auction.product.domain.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "The name is required")
    private String name;

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @Column(name = "end_date_stored")
    private Timestamp endDateStored;

    @Column(name = "start_price", nullable = false)
    @Min(value = 1, message = "The start price must be at least 1")
    private Double startPrice;

    @Column(name = "last_price")
    private Double lastPrice;

    @Column(name = "deposit_price", nullable = false)
    @Min(value = 1, message = "The deposit price must be at least 1")
    private Double depositPrice;

    @Column(name = "step_price", nullable = false)
    @Min(value = 1, message = "The step price must be at least 1")
    private Double stepPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, length = 20)
    private AuctionState state;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "staff_id")
    private User user;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AuctionHistory> auctionHistories;
}
