package br.com.customerwallet.service;

import br.com.customerwallet.exception.WalletCustomerNotFoundException;
import br.com.customerwallet.exception.WalletCustomerSaveException;
import br.com.customerwallet.model.entity.WalletCustomer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WalletCustomerServiceTest {

    @Autowired
    private WalletCustomerService walletCustomerService;

    @Test
    void saveWalletCustomerSuccess() {
        final WalletCustomer walletCustomer = new WalletCustomer();
        walletCustomer.setCustomerId(1L);
        walletCustomer.setBalance(BigDecimal.ZERO);

        final WalletCustomer walletCustomerSaved = walletCustomerService.save(walletCustomer);

        Assertions.assertNotNull(walletCustomerSaved.getId());
    }

    @Test
    void saveWalletCustomerCustomerIdRequiredFailure() {
        Assertions.assertThrowsExactly(WalletCustomerSaveException.class, () -> {
            final WalletCustomer walletCustomer = new WalletCustomer();
            walletCustomer.setBalance(BigDecimal.ZERO);

            walletCustomerService.save(walletCustomer);
        });
    }

    @Test
    void saveWalletCustomerBalanceRequiredFailure() {
        Assertions.assertThrowsExactly(WalletCustomerSaveException.class, () -> {
            final WalletCustomer walletCustomer = new WalletCustomer();
            walletCustomer.setCustomerId(1L);

            walletCustomerService.save(walletCustomer);
        });
    }

    @Test
    void checkEnoughBalanceTrueSucess() {
        final WalletCustomer walletCustomer = new WalletCustomer();
        walletCustomer.setCustomerId(1L);
        walletCustomer.setBalance(BigDecimal.TEN);

        final WalletCustomer walletCustomerSaved = walletCustomerService.save(walletCustomer);

        Assertions.assertTrue(walletCustomerService.checkEnoughBalance(walletCustomerSaved.getCustomerId(), BigDecimal.ONE));
    }

    @Test
    void checkEnoughBalanceFalseSucess() {
        final WalletCustomer walletCustomer = new WalletCustomer();
        walletCustomer.setCustomerId(1L);
        walletCustomer.setBalance(BigDecimal.ONE);

        final WalletCustomer walletCustomerSaved = walletCustomerService.save(walletCustomer);

        Assertions.assertFalse(walletCustomerService.checkEnoughBalance(walletCustomerSaved.getCustomerId(), BigDecimal.TEN));
    }

    // TODO: 24/11/2023 A query de updateBalance não funciona no H2 somente no postgres para posteriormente criar testes para os métodos addAmountToBalance e updateBalance

    @Test
    void findByCustomerIdSucess() {
        final WalletCustomer walletCustomer = new WalletCustomer();
        walletCustomer.setCustomerId(1L);
        walletCustomer.setBalance(BigDecimal.ONE);

        final WalletCustomer walletCustomerSaved = walletCustomerService.save(walletCustomer);

        Assertions.assertEquals(walletCustomerSaved.getId(), walletCustomerService.findByCustomerId(walletCustomerSaved.getCustomerId()).getId());
    }

    @Test
    void findByCustomerIdFailure() {
        Assertions.assertThrowsExactly(WalletCustomerNotFoundException.class, () -> {
            walletCustomerService.findByCustomerId(1L);
        });
    }
}
