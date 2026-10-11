package vn.webapp.backend.auction.auction.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import vn.webapp.backend.auction.auction.domain.enums.AuctionState;
import vn.webapp.backend.auction.product.domain.enums.ProductMaterial;

/**
 * Public, read-only representation of an auction catalogue item.
 *
 * <p>This deliberately contains only information that can be shown to an
 * unauthenticated visitor. It must not expose staff, seller, registration, or
 * transaction records.</p>
 */
@Data
@Builder
public class AuctionCatalogItemResponse {

	private Integer id;
	private String code;
	private String name;
	private AuctionState state;
	private Instant startDate;
	private Instant endDate;
	private BigDecimal startPrice;
	private BigDecimal currentPrice;
	private BigDecimal depositPrice;
	private BigDecimal stepPrice;
	private long bidCount;
	private ProductSummary product;

	@Data
	@Builder
	public static class ProductSummary {
		private Integer id;
		private String name;
		private String description;
		private String brand;
		private ProductMaterial material;
		private Double weight;
		private Integer categoryId;
		private String categoryName;
		private List<String> imageUrls;
	}
}
