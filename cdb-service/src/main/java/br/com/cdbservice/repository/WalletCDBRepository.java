package br.com.cdbservice.repository;

import br.com.cdbservice.model.entity.WalletCDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletCDBRepository extends JpaRepository<WalletCDB, Long> {
    List<WalletCDB> findAllByCustomerId(final Long customerId);

    Optional<WalletCDB> findByCustomerIdAndPaperId(final Long customerId, final Long paperId);
}
