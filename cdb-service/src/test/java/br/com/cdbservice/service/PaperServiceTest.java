package br.com.cdbservice.service;

import br.com.cdbservice.exception.PaperNotFoundException;
import br.com.cdbservice.exception.PaperSaveException;
import br.com.cdbservice.model.entity.Paper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Collections;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PaperServiceTest {

    @Autowired
    private PaperService paperService;

    @Test
    void savePaperSuccess() {
        final Paper paper = new Paper();
        paper.setValue(BigDecimal.ONE);

        final Paper paperSaved = paperService.save(paper);

        Assertions.assertNotNull(paperSaved.getId());
    }

    @Test
    void savePaperValueRequiredFailure() {
        Assertions.assertThrowsExactly(PaperSaveException.class, () -> {
            final Paper paper = new Paper();

            paperService.save(paper);
        });
    }

    @Test
    void findAllEmptyListSucess() {
        Assertions.assertEquals(Collections.emptyList(), paperService.findAll());
    }

    @Test
    void findAllWithElementListSucess() {
        final Paper paper = new Paper();
        paper.setValue(BigDecimal.ONE);

        final Paper paperSaved = paperService.save(paper);

        Assertions.assertNotEquals(Collections.emptyList(), paperService.findAll());
    }

    @Test
    void findByIdPaperThatExistsSucess() {
        final Paper paper = new Paper();
        paper.setValue(BigDecimal.ONE);

        final Paper paperSaved = paperService.save(paper);

        Assertions.assertEquals(paperService.findById(paperSaved.getId()).getId(), paperSaved.getId());
    }

    @Test
    void findByIdPaperThatNotExistsFailure() {
        Assertions.assertThrowsExactly(PaperNotFoundException.class, () -> {
            paperService.findById(1L);
        });
    }
}
