package br.com.cdbservice.repository;

import br.com.cdbservice.model.entity.WalletCDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletCDBRepository extends JpaRepository<WalletCDB, Long> {
}
