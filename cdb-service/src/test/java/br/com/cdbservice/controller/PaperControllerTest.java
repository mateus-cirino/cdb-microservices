package br.com.cdbservice.controller;

import br.com.cdbservice.exception.PaperNotFoundException;
import br.com.cdbservice.exception.PaperSaveException;
import br.com.cdbservice.model.dto.PaperDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Collections;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PaperControllerTest {

    @Autowired
    private PaperController paperController;

    @Test
    void savePaperSuccess() {
        final PaperDTO paperDTO = new PaperDTO();
        paperDTO.setValue(BigDecimal.ONE);

        final PaperDTO paperSaved = paperController.save(paperDTO).getBody();

        Assertions.assertNotNull(paperSaved.getId());
    }

    @Test
    void savePaperValueRequiredFailure() {
        Assertions.assertThrowsExactly(PaperSaveException.class, () -> {
            final PaperDTO paperDTO = new PaperDTO();

            paperController.save(paperDTO);
        });
    }

    @Test
    void findAllEmptyListSucess() {
        Assertions.assertEquals(Collections.emptyList(), paperController.findAll().getBody());
    }

    @Test
    void findAllWithElementListSucess() {
        final PaperDTO paperDTO = new PaperDTO();
        paperDTO.setValue(BigDecimal.ONE);

        final PaperDTO paperSaved = paperController.save(paperDTO).getBody();

        Assertions.assertNotNull(paperSaved.getId());

        Assertions.assertNotEquals(Collections.emptyList(), paperController.findAll().getBody());
    }

    @Test
    void findByIdPaperThatExistsSucess() {
        final PaperDTO paperDTO = new PaperDTO();
        paperDTO.setValue(BigDecimal.ONE);

        final PaperDTO paperSaved = paperController.save(paperDTO).getBody();

        Assertions.assertEquals(paperController.findById(paperSaved.getId()).getBody().getId(), paperSaved.getId());
    }

    @Test
    void findByIdPaperThatNotExistsFailure() {
        Assertions.assertThrowsExactly(PaperNotFoundException.class, () -> {
            paperController.findById(1L);
        });
    }
}
