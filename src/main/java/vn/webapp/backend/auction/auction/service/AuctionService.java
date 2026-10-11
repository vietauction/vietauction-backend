package vn.webapp.backend.auction.auction.service;

import java.time.OffsetDateTime;

import org.springframework.data.domain.Page;

import vn.webapp.backend.auction.auction.domain.enums.AuctionState;
import vn.webapp.backend.auction.auction.dto.response.AuctionCatalogItemResponse;

public interface AuctionService {
	Page<AuctionCatalogItemResponse> getPublicCatalog(
			int page,
			int limit,
			String keyword,
			AuctionState state,
			Integer categoryId,
			OffsetDateTime startDate,
			OffsetDateTime endDate,
			String sort);

	AuctionCatalogItemResponse getPublicAuctionById(Integer id);
}
