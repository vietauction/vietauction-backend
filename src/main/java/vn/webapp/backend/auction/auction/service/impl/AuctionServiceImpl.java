package vn.webapp.backend.auction.auction.service.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.auction.domain.entity.Auction;
import vn.webapp.backend.auction.auction.domain.enums.AuctionState;
import vn.webapp.backend.auction.auction.domain.repository.AuctionHistoryRepository;
import vn.webapp.backend.auction.auction.domain.repository.AuctionRepository;
import vn.webapp.backend.auction.auction.dto.response.AuctionCatalogItemResponse;
import vn.webapp.backend.auction.auction.service.AuctionService;
import vn.webapp.backend.auction.product.domain.entity.Image;
import vn.webapp.backend.auction.product.domain.entity.Product;
import vn.webapp.backend.auction.product.domain.entity.ProductCategory;
import vn.webapp.backend.auction.shared.exception.AppException;
import vn.webapp.backend.auction.shared.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

	private static final int MAX_PAGE_SIZE = 50;

	private final AuctionRepository auctionRepository;
	private final AuctionHistoryRepository auctionHistoryRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<AuctionCatalogItemResponse> getPublicCatalog(
			int page,
			int limit,
			String keyword,
			AuctionState state,
			Integer categoryId,
			OffsetDateTime startDate,
			OffsetDateTime endDate,
			String sort) {
		Pageable pageable = PageRequest.of(
				Math.max(page - 1, 0),
				Math.min(Math.max(limit, 1), MAX_PAGE_SIZE),
				toSort(sort));

		return auctionRepository.findAll(
				buildCatalogSpecification(keyword, state, categoryId, startDate, endDate), pageable)
				.map(this::toCatalogItem);
	}

	@Override
	@Transactional(readOnly = true)
	public AuctionCatalogItemResponse getPublicAuctionById(Integer id) {
		Auction auction = auctionRepository.findById(id)
				.orElseThrow(() -> new AppException(ErrorCode.AUCTION_NOT_FOUND));

		return toCatalogItem(auction);
	}

	private Specification<Auction> buildCatalogSpecification(
			String keyword,
			AuctionState state,
			Integer categoryId,
			OffsetDateTime startDate,
			OffsetDateTime endDate) {
		return (root, query, criteriaBuilder) -> {
			var predicate = criteriaBuilder.conjunction();

			if (state != null) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("state"), state));
			}

			if (startDate != null) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"),
								java.sql.Timestamp.from(startDate.toInstant())));
			}

			if (endDate != null) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.lessThanOrEqualTo(root.get("endDate"),
								java.sql.Timestamp.from(endDate.toInstant())));
			}

			boolean needsProductJoin = categoryId != null || (keyword != null && !keyword.isBlank());
			if (needsProductJoin) {
				Join<Auction, Product> product = root.join("product", JoinType.LEFT);

				if (categoryId != null) {
					predicate = criteriaBuilder.and(predicate,
							criteriaBuilder.equal(product.join("category", JoinType.LEFT).get("id"), categoryId));
				}

				if (keyword != null && !keyword.isBlank()) {
					String searchTerm = "%" + keyword.trim().toLowerCase() + "%";
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(
							criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchTerm),
							criteriaBuilder.like(criteriaBuilder.lower(product.get("name")), searchTerm)));
				}
			}

			return predicate;
		};
	}

	private Sort toSort(String sort) {
		if ("ending-soon".equals(sort)) {
			return Sort.by(Sort.Direction.ASC, "endDate");
		}
		if ("price-asc".equals(sort)) {
			return Sort.by(Sort.Direction.ASC, "startPrice");
		}
		if ("price-desc".equals(sort)) {
			return Sort.by(Sort.Direction.DESC, "startPrice");
		}

		return Sort.by(Sort.Direction.DESC, "startDate");
	}

	private AuctionCatalogItemResponse toCatalogItem(Auction auction) {
		return AuctionCatalogItemResponse.builder()
				.id(auction.getId())
				.code("VA-%04d".formatted(auction.getId()))
				.name(auction.getName())
				.state(auction.getState())
				.startDate(toInstant(auction.getStartDate()))
				.endDate(toInstant(auction.getEndDate()))
				.startPrice(toBigDecimal(auction.getStartPrice()))
				.currentPrice(toBigDecimal(auction.getLastPrice()))
				.depositPrice(toBigDecimal(auction.getDepositPrice()))
				.stepPrice(toBigDecimal(auction.getStepPrice()))
				.bidCount(auctionHistoryRepository.countByAuction_Id(auction.getId()))
				.product(toProductSummary(auction.getProduct()))
				.build();
	}

	private AuctionCatalogItemResponse.ProductSummary toProductSummary(Product product) {
		if (product == null) {
			return null;
		}

		ProductCategory category = product.getCategory();
		List<String> imageUrls = product.getImages() == null
				? List.of()
				: product.getImages().stream()
						.map(Image::getData)
						.filter(url -> url != null && !url.isBlank())
						.toList();

		return AuctionCatalogItemResponse.ProductSummary.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.brand(product.getBrand())
				.material(product.getMaterial())
				.weight(product.getWeight())
				.categoryId(category == null ? null : category.getId())
				.categoryName(category == null ? null : category.getName())
				.imageUrls(imageUrls)
				.build();
	}

	private BigDecimal toBigDecimal(Double value) {
		return value == null ? null : BigDecimal.valueOf(value);
	}

	private Instant toInstant(java.sql.Timestamp value) {
		return value == null ? null : value.toInstant();
	}
}
