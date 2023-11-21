package br.com.cdbservice.service;

import br.com.cdbservice.model.entity.WalletCDB;
import br.com.cdbservice.repository.WalletCDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletCDBService {

    @Autowired
    private WalletCDBRepository walletCDBRepository;

    public WalletCDB save(final WalletCDB walletCDB) {
        return walletCDBRepository.saveAndFlush(walletCDB);
    }

    public List<WalletCDB> findAll() {
        return walletCDBRepository.findAll();
    }

    public WalletCDB findById(final Long id) {
        return walletCDBRepository.findById(id).orElse(new WalletCDB());
    }
}
