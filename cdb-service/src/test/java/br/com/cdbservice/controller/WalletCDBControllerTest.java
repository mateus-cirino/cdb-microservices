package br.com.cdbservice.controller;

import br.com.cdbservice.exception.WalletCDBNotFoundException;
import br.com.cdbservice.exception.WalletCDBSaveException;
import br.com.cdbservice.model.dto.PaperDTO;
import br.com.cdbservice.model.dto.WalletCDBDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Collections;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WalletCDBControllerTest {

    @Autowired
    private WalletCDBController walletCDBController;

    @Autowired
    private PaperController paperController;

    @Test
    void saveWalletCDBSuccess() {
        final PaperDTO paperDTO = new PaperDTO();
        paperDTO.setValue(BigDecimal.ONE);
        paperDTO.setId(paperController.save(paperDTO).getBody().getId());

        final WalletCDBDTO walletCDBDTO = new WalletCDBDTO();
        walletCDBDTO.setPaper(paperDTO);
        walletCDBDTO.setAmount(BigDecimal.ONE);
        walletCDBDTO.setCustomerId(1L);

        final WalletCDBDTO walletCDBSaved = walletCDBController.save(walletCDBDTO).getBody();

        Assertions.assertNotNull(walletCDBSaved.getId());
    }

    @Test
    void saveWalletCDBAmountRequiredFailure() {
        Assertions.assertThrowsExactly(WalletCDBSaveException.class, () -> {
            final PaperDTO paperDTO = new PaperDTO();
            paperDTO.setValue(BigDecimal.ONE);
            paperDTO.setId(paperController.save(paperDTO).getBody().getId());

            final WalletCDBDTO walletCDBDTO = new WalletCDBDTO();
            walletCDBDTO.setPaper(paperDTO);
            walletCDBDTO.setCustomerId(1L);

            walletCDBController.save(walletCDBDTO);
        });
    }

    @Test
    void saveWalletCDBCustomerIdRequiredFailure() {
        Assertions.assertThrowsExactly(WalletCDBSaveException.class, () -> {
            final PaperDTO paperDTO = new PaperDTO();
            paperDTO.setValue(BigDecimal.ONE);
            paperDTO.setId(paperController.save(paperDTO).getBody().getId());

            final WalletCDBDTO walletCDBDTO = new WalletCDBDTO();
            walletCDBDTO.setPaper(paperDTO);
            walletCDBDTO.setAmount(BigDecimal.ONE);

            walletCDBController.save(walletCDBDTO);
        });
    }

    @Test
    void saveWalletCDBValueMultiplySucess() {
        final PaperDTO paperDTO = new PaperDTO();
        paperDTO.setValue(BigDecimal.ONE);
        paperDTO.setId(paperController.save(paperDTO).getBody().getId());

        final WalletCDBDTO walletCDBDTO = new WalletCDBDTO();
        walletCDBDTO.setPaper(paperDTO);
        walletCDBDTO.setAmount(BigDecimal.TEN);
        walletCDBDTO.setCustomerId(1L);

        final WalletCDBDTO walletCDBSaved = walletCDBController.save(walletCDBDTO).getBody();

        Assertions.assertEquals(walletCDBSaved.getValue(), BigDecimal.TEN);
    }

    @Test
    void findAllEmptyListSucess() {
        Assertions.assertEquals(Collections.emptyList(), walletCDBController.findAll().getBody());
    }

    @Test
    void findAllWithElementListSucess() {
        final PaperDTO paperDTO = new PaperDTO();
        paperDTO.setValue(BigDecimal.ONE);
        paperDTO.setId(paperController.save(paperDTO).getBody().getId());

        final WalletCDBDTO walletCDBDTO = new WalletCDBDTO();
        walletCDBDTO.setPaper(paperDTO);
        walletCDBDTO.setAmount(BigDecimal.ONE);
        walletCDBDTO.setCustomerId(1L);

        final WalletCDBDTO walletCDBSaved = walletCDBController.save(walletCDBDTO).getBody();

        Assertions.assertNotNull(walletCDBSaved.getId());

        Assertions.assertNotEquals(Collections.emptyList(), walletCDBController.findAll().getBody());
    }

    @Test
    void findByIdWalletCDBThatExistsSucess() {
        final PaperDTO paperDTO = new PaperDTO();
        paperDTO.setValue(BigDecimal.ONE);
        paperDTO.setId(paperController.save(paperDTO).getBody().getId());

        final WalletCDBDTO walletCDBDTO = new WalletCDBDTO();
        walletCDBDTO.setPaper(paperDTO);
        walletCDBDTO.setAmount(BigDecimal.ONE);
        walletCDBDTO.setCustomerId(1L);

        final WalletCDBDTO walletCDBSaved = walletCDBController.save(walletCDBDTO).getBody();

        Assertions.assertEquals(walletCDBController.findById(walletCDBSaved.getId()).getBody().getId(), walletCDBSaved.getId());
    }

    @Test
    void findByIdWalletCDBThatNotExistsFailure() {
        Assertions.assertThrowsExactly(WalletCDBNotFoundException.class, () -> {
            walletCDBController.findById(1L);
        });
    }
}
