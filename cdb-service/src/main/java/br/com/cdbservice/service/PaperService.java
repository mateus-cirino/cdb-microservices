package br.com.cdbservice.service;

import br.com.cdbservice.exception.PaperNotFoundException;
import br.com.cdbservice.exception.PaperSaveException;
import br.com.cdbservice.model.entity.Paper;
import br.com.cdbservice.repository.PaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;

    public Paper save(final Paper paper) {
        try {
            log.info("Tentando persistir o paper.");
            final Paper newPaper = paperRepository.save(paper);
            paper.setId(newPaper.getId());
            log.info(String.format("Persistência concluída com sucesso. Id do paper: %s.", paper.getId()));
        } catch (final Exception e) {
            log.error(String.format("Não foi possível persistir o paper, mensagem de erro: %s. Classe: %s, método: save.", e.getMessage(), PaperService.class.getSimpleName()));
            throw new PaperSaveException(e.getMessage());
        }

        return paper;
    }

    public Paper findById(final Long id) {
        log.info(String.format("Tentando buscar o paper de id %s.", id));

        final Optional<Paper> optPaper = paperRepository.findById(id);

        final Paper paperFound;

        if (optPaper.isPresent()) {
            log.info(String.format("Paper de id %s encontrado com sucesso.", id));

            paperFound = optPaper.get();
        } else {
            throw new PaperNotFoundException(String.format("O paper de id %s não existe", id));
        }

        return paperFound;
    }

    public List<Paper> findAll() {
        log.info("Tentando buscar todos os papers");

        final List<Paper> papersFound = paperRepository.findAll();

        log.info("Busca de todos os papers foi concluída com sucesso.");

        return papersFound;
    }
}
