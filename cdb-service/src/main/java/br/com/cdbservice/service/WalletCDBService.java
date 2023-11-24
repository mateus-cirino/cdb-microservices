package br.com.cdbservice.service;

import br.com.cdbservice.exception.WalletCDBNotFoundException;
import br.com.cdbservice.exception.WalletCDBSaveException;
import br.com.cdbservice.model.entity.WalletCDB;
import br.com.cdbservice.repository.WalletCDBRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WalletCDBService {

    @Autowired
    private WalletCDBRepository walletCDBRepository;

    @Autowired
    private PaperService paperService;

    public WalletCDB save(final WalletCDB walletCDB) {
        try {
            log.info("Tentando persistir o walletCDB.");
            final WalletCDB newWalletCDB = walletCDBRepository.save(walletCDB);
            walletCDB.setId(newWalletCDB.getId());
            walletCDB.setPaper(paperService.findById(walletCDB.getPaper().getId()));
            log.info(String.format("Persistência concluída com sucesso. Id do walletCDB: %s.", walletCDB.getId()));
        } catch (final Exception e) {
            log.error(String.format("Não foi possível persistir o walletCDB, mensagem de erro: %s. Classe: %s, método: save.", e.getMessage(), WalletCDBService.class.getSimpleName()));
            throw new WalletCDBSaveException(e.getMessage());
        }

        return walletCDB;
    }

    public WalletCDB findById(final Long id) {
        log.info(String.format("Tentando buscar o walletCDB de id %s.", id));

        final Optional<WalletCDB> optWalletCDB = walletCDBRepository.findById(id);

        final WalletCDB walletCDBFound;

        if (optWalletCDB.isPresent()) {
            log.info(String.format("WalletCDB de id %s encontrado com sucesso.", id));

            walletCDBFound = optWalletCDB.get();
        } else {
            throw new WalletCDBNotFoundException(String.format("O walletCDB de id %s não existe", id));
        }

        return walletCDBFound;
    }

    public List<WalletCDB> findAll() {
        log.info("Tentando buscar todos os walletCDBs");

        final List<WalletCDB> walletCDBsFound = walletCDBRepository.findAll();

        log.info("Busca de todos os walletCDBs foi concluída com sucesso.");

        return walletCDBsFound;
    }

    public List<WalletCDB> findAllByCustomerId(final Long customerId) {
        return walletCDBRepository.findAllByCustomerId(customerId);
    }

    public WalletCDB findByCustomerIdAndPaperId(final Long customerId, final Long paperId) {
        final Optional<WalletCDB> optWalletCDB = walletCDBRepository.findByCustomerIdAndPaperId(customerId, paperId);

        final WalletCDB walletCDBFound;

        if (optWalletCDB.isPresent()) {
            walletCDBFound = optWalletCDB.get();
        } else {
            throw new WalletCDBNotFoundException(String.format("O walletCDB do customer de id %s e paper de id %s", customerId, paperId));
        }

        return walletCDBFound;
    }

    public void delete(final WalletCDB walletCDB) {
        walletCDBRepository.delete(walletCDB);
    }
}
