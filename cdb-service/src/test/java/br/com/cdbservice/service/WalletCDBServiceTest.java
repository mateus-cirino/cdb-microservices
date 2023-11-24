package br.com.cdbservice.service;

import br.com.cdbservice.exception.WalletCDBNotFoundException;
import br.com.cdbservice.exception.WalletCDBSaveException;
import br.com.cdbservice.model.entity.Paper;
import br.com.cdbservice.model.entity.WalletCDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WalletCDBServiceTest {

    @Autowired
    private WalletCDBService walletCDBService;

    @Autowired
    private PaperService paperService;

    @Test
    void saveWalletCDBSuccess() {
        final WalletCDB walletCDB = new WalletCDB();
        walletCDB.setCustomerId(1L);
        walletCDB.setAmount(BigDecimal.ONE);

        final Paper paper = new Paper();
        paper.setValue(BigDecimal.ONE);

        walletCDB.setPaper(paperService.save(paper));

        final WalletCDB walletCDBSaved = walletCDBService.save(walletCDB);

        Assertions.assertNotNull(walletCDBSaved.getId());
    }

    @Test
    void saveWalletCDBCustomerIdRequiredFailure() {
        Assertions.assertThrowsExactly(WalletCDBSaveException.class, () -> {
            final WalletCDB walletCDB = new WalletCDB();
            walletCDB.setAmount(BigDecimal.ONE);

            final Paper paper = new Paper();
            paper.setValue(BigDecimal.ONE);

            walletCDB.setPaper(paperService.save(paper));

            walletCDBService.save(walletCDB);
        });
    }

    @Test
    void saveWalletCDBAmountRequiredFailure() {
        Assertions.assertThrowsExactly(WalletCDBSaveException.class, () -> {
            final WalletCDB walletCDB = new WalletCDB();
            walletCDB.setCustomerId(1L);

            final Paper paper = new Paper();
            paper.setValue(BigDecimal.ONE);

            walletCDB.setPaper(paperService.save(paper));

            walletCDBService.save(walletCDB);
        });
    }

    @Test
    void saveWalletCDBPaperRequiredFailure() {
        Assertions.assertThrowsExactly(WalletCDBSaveException.class, () -> {
            final WalletCDB walletCDB = new WalletCDB();
            walletCDB.setCustomerId(1L);
            walletCDB.setAmount(BigDecimal.ONE);

            walletCDBService.save(walletCDB);
        });
    }

    @Test
    void findByIdWalletCDBThatNotExistsFailure() {
        Assertions.assertThrowsExactly(WalletCDBNotFoundException.class, () -> {
            walletCDBService.findById(1L);
        });
    }

    @Test
    void findAllEmptyListSucess() {
        Assertions.assertTrue(walletCDBService.findAll().isEmpty());
    }

    @Test
    void findAllWithElementListSucess() {
        final WalletCDB walletCDB = new WalletCDB();
        walletCDB.setCustomerId(1L);
        walletCDB.setAmount(BigDecimal.ONE);

        final Paper paper = new Paper();
        paper.setValue(BigDecimal.ONE);

        walletCDB.setPaper(paperService.save(paper));

        walletCDBService.save(walletCDB);

        Assertions.assertFalse(walletCDBService.findAll().isEmpty());
    }

    @Test
    void findAllByCustomerIdWithElement() {
        final WalletCDB walletCDB = new WalletCDB();
        walletCDB.setCustomerId(1L);
        walletCDB.setAmount(BigDecimal.ONE);

        final Paper paper = new Paper();
        paper.setValue(BigDecimal.ONE);

        walletCDB.setPaper(paperService.save(paper));

        walletCDBService.save(walletCDB);

        Assertions.assertFalse(walletCDBService.findAllByCustomerId(walletCDB.getCustomerId()).isEmpty());
    }

    @Test
    void findByCustomerIdAndPaperIdWithElement() {
        final WalletCDB walletCDB = new WalletCDB();
        walletCDB.setCustomerId(1L);
        walletCDB.setAmount(BigDecimal.ONE);

        final Paper paper = new Paper();
        paper.setValue(BigDecimal.ONE);

        walletCDB.setPaper(paperService.save(paper));

        walletCDBService.save(walletCDB);

        Assertions.assertNotNull(walletCDBService.findByCustomerIdAndPaperId(walletCDB.getCustomerId(), walletCDB.getPaper().getId()).getId());
    }
}
