package vn.webapp.backend.auction.identity.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.webapp.backend.auction.identity.domain.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Integer> {}
