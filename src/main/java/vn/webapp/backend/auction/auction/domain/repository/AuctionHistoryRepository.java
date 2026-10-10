package vn.webapp.backend.auction.auction.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.webapp.backend.auction.auction.domain.entity.AuctionHistory;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Integer> {
}
