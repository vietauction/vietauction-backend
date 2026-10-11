package vn.webapp.backend.auction.auction.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.webapp.backend.auction.auction.domain.entity.Auction;
import vn.webapp.backend.auction.auction.domain.enums.AuctionState;
import vn.webapp.backend.auction.auction.domain.repository.AuctionHistoryRepository;
import vn.webapp.backend.auction.auction.domain.repository.AuctionRepository;
import vn.webapp.backend.auction.product.domain.entity.Image;
import vn.webapp.backend.auction.product.domain.entity.Product;
import vn.webapp.backend.auction.product.domain.entity.ProductCategory;
import vn.webapp.backend.auction.product.domain.enums.ProductMaterial;
import vn.webapp.backend.auction.shared.exception.AppException;
import vn.webapp.backend.auction.shared.exception.ErrorCode;

@ExtendWith(MockitoExtension.class)
class AuctionServiceImplTest {

	@Mock
	private AuctionRepository auctionRepository;

	@Mock
	private AuctionHistoryRepository auctionHistoryRepository;

	@InjectMocks
	private AuctionServiceImpl auctionService;

	@Test
	void getPublicAuctionByIdReturnsPublicCatalogueContract() {
		Auction auction = auctionFixture();
		when(auctionRepository.findById(8)).thenReturn(Optional.of(auction));
		when(auctionHistoryRepository.countByAuction_Id(8)).thenReturn(4L);

		var response = auctionService.getPublicAuctionById(8);

		assertThat(response.getCode()).isEqualTo("VA-0008");
		assertThat(response.getCurrentPrice()).isEqualByComparingTo("12500000");
		assertThat(response.getBidCount()).isEqualTo(4);
		assertThat(response.getProduct().getCategoryName()).isEqualTo("Nhẫn");
		assertThat(response.getProduct().getImageUrls()).containsExactly("https://cdn.example.vn/ring.jpg");
	}

	@Test
	void getPublicCatalogCapsPageSizeAndMapsResults() {
		Auction auction = auctionFixture();
		when(auctionRepository.findAll(any(Specification.class), any(Pageable.class)))
				.thenAnswer(invocation -> new PageImpl<>(List.of(auction), invocation.getArgument(1), 1));
		when(auctionHistoryRepository.countByAuction_Id(8)).thenReturn(4L);

		var result = auctionService.getPublicCatalog(0, 999, "kim cương", AuctionState.ONGOING,
				null, null, null, "ending-soon");

		assertThat(result.getNumber()).isZero();
		assertThat(result.getSize()).isEqualTo(50);
		assertThat(result.getContent()).extracting("code").containsExactly("VA-0008");
	}

	@Test
	void getPublicAuctionByIdRejectsUnknownAuction() {
		when(auctionRepository.findById(999)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> auctionService.getPublicAuctionById(999))
				.isInstanceOfSatisfying(AppException.class,
						exception -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.AUCTION_NOT_FOUND));
	}

	private Auction auctionFixture() {
		ProductCategory category = ProductCategory.builder().id(2).name("Nhẫn").build();
		Product product = Product.builder()
				.id(3)
				.name("Nhẫn kim cương Art Deco")
				.description("Hồ sơ kiểm định đầy đủ")
				.brand("VietAuction")
				.material(ProductMaterial.DIAMOND)
				.weight(3.2)
				.category(category)
				.images(List.of(Image.builder().data("https://cdn.example.vn/ring.jpg").build()))
				.build();

		return Auction.builder()
				.id(8)
				.name("Đấu giá nhẫn kim cương Art Deco")
				.state(AuctionState.ONGOING)
				.startDate(Timestamp.from(Instant.parse("2026-10-10T02:00:00Z")))
				.endDate(Timestamp.from(Instant.parse("2026-10-10T09:00:00Z")))
				.startPrice(10000000D)
				.lastPrice(12500000D)
				.depositPrice(1000000D)
				.stepPrice(500000D)
				.product(product)
				.build();
	}
}
