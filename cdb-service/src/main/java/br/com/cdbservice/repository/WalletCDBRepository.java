package br.com.cdbservice.repository;

import br.com.cdbservice.model.entity.WalletCDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletCDBRepository extends JpaRepository<WalletCDB, Long> {
    List<WalletCDB> findAllByCustomerId(final Long customerId);
}
