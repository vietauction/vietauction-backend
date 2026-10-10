package vn.webapp.backend.auction.transaction.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import vn.webapp.backend.auction.transaction.domain.enums.RequestApprovalState;
import vn.webapp.backend.auction.identity.domain.entity.User;
import vn.webapp.backend.auction.product.domain.entity.Product;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "request_approval")
public class RequestApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "staff_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User staff;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id_send")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User sender;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id_respond")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User responder;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "product_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Product product;

    @Column(name = "valuation")
    private double valuation;

    @Column(name = "desired_price", nullable = false)
    private double desiredPrice;

    @Column(name = "request_time", nullable = false)
    private Timestamp requestTime;

    @Column(name = "response_time")
    private Timestamp responseTime;

    @Enumerated(EnumType.STRING)
    private RequestApprovalState state;

    @Column(name = "is_confirm")
    private boolean isConfirm;

    @Column(name = "note")
    private String note;
}
