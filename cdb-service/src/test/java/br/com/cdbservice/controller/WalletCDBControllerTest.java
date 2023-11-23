package br.com.cdbservice.controller;

import br.com.cdbservice.exception.WalletCDBNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WalletCDBControllerTest {

    @Autowired
    private WalletCDBController walletCDBController;

    @Autowired
    private PaperController paperController;

    @Test
    void findByIdWalletCDBThatNotExistsFailure() {
        Assertions.assertThrowsExactly(WalletCDBNotFoundException.class, () -> {
            walletCDBController.findById(1L);
        });
    }
}
