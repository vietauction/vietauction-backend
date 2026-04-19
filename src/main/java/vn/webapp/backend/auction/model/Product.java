package vn.webapp.backend.auction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import vn.webapp.backend.auction.enums.ProductMaterial;
import vn.webapp.backend.auction.enums.ProductState;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "The price required")
    private String name;

    @Column(name = "buy_now_price")
    @Min(value = 1, message = "The price must be at least 1")
    private Double buyNowPrice;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductMaterial material;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "is_holding")
    private Boolean isHolding;

    @Column(name = "received_date")
    private Timestamp receivedDate;

    @Column(name = "delivery_date")
    private Timestamp deliveryDate;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductState state;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Image> images;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH
    })
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ProductCategory category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<RequestApproval> requestApprovals;
}
