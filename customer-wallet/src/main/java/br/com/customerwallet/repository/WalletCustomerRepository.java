package br.com.customerwallet.repository;

import br.com.customerwallet.model.entity.WalletCustomer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface WalletCustomerRepository extends JpaRepository<WalletCustomer, Long> {
    Optional<WalletCustomer> findByCustomerIdAndBalanceIsGreaterThanEqual(final Long customerId, final BigDecimal value);

    Optional<WalletCustomer> findByCustomerId(final Long customerId);

    @Transactional
    @Modifying
    @Query("UPDATE WalletCustomer w SET w.balance = w.balance + :amount WHERE w.customerId = :customerId")
    void updateBalance(final Long customerId, final BigDecimal amount);
}
