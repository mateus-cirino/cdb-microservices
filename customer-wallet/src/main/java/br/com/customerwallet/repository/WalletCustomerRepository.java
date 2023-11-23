package br.com.customerwallet.repository;

import br.com.customerwallet.model.entity.WalletCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface WalletCustomerRepository extends JpaRepository<WalletCustomer, Long> {
    Optional<WalletCustomer> findByCustomerIdAndBalanceIsGreaterThanEqual(final Long customerId, final BigDecimal value);
}
