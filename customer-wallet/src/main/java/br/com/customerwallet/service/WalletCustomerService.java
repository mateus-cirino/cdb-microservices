package br.com.customerwallet.service;

import br.com.customerwallet.exception.WalletCustomerNotFoundException;
import br.com.customerwallet.exception.WalletCustomerSaveException;
import br.com.customerwallet.model.entity.WalletCustomer;
import br.com.customerwallet.repository.WalletCustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class WalletCustomerService {

    @Autowired
    private WalletCustomerRepository walletCustomerRepository;

    public WalletCustomer save(final WalletCustomer walletCustomer) {
        try {
            log.info("Tentando persistir o walletCustomer.");
            final WalletCustomer newWalletCustomer = walletCustomerRepository.save(walletCustomer);
            walletCustomer.setId(newWalletCustomer.getId());
            log.info(String.format("Persistência concluída com sucesso. Id do walletCustomer: %s.", walletCustomer.getId()));
        } catch (final Exception e) {
            log.error(String.format("Não foi possível persistir o walletCustomer, mensagem de erro: %s. Classe: %s, método: save.", e.getMessage(), WalletCustomerService.class.getSimpleName()));
            throw new WalletCustomerSaveException(e.getMessage());
        }

        return walletCustomer;
    }

    public Boolean checkEnoughBalance(final Long customerId, final BigDecimal value) {
        return walletCustomerRepository.findByCustomerIdAndBalanceIsGreaterThanEqual(customerId, value).isPresent();
    }

    public BigDecimal addAmountToBalance(final Long customerId, final BigDecimal amount) {
        return walletCustomerRepository.addAmountToBalance(customerId, amount);
    }

    public WalletCustomer findById(final Long id) {
        log.info(String.format("Tentando buscar o walletCustomer de id %s.", id));

        final Optional<WalletCustomer> optWalletCustomer = walletCustomerRepository.findById(id);

        final WalletCustomer walletCustomerFound;

        if (optWalletCustomer.isPresent()) {
            log.info(String.format("WalletCustomer de id %s encontrado com sucesso.", id));

            walletCustomerFound = optWalletCustomer.get();
        } else {
            throw new WalletCustomerNotFoundException(String.format("O walletCustomer de id %s não existe", id));
        }

        return walletCustomerFound;
    }
}
