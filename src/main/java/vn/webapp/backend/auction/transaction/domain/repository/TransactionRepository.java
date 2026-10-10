package vn.webapp.backend.auction.transaction.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.webapp.backend.auction.transaction.domain.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
