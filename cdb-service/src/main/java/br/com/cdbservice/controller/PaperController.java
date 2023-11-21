package br.com.cdbservice.controller;

import br.com.cdbservice.exception.PaperSaveException;
import br.com.cdbservice.model.dto.PaperDTO;
import br.com.cdbservice.model.entity.Paper;
import br.com.cdbservice.service.PaperService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/cdb-service/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;
    @PostMapping(value = "/save")
    public ResponseEntity<PaperDTO> save(@Valid @RequestBody PaperDTO paperDTO) {
        log.info("Iniciando o processo de persistência do paper.");
        log.info(String.format("Dados que foram recebidos via @RequestBody: %s", paperDTO.toString()));

        final Paper paper = paperDTO.fromDTO();

        log.info("Criação da entidade paper concluída com sucesso.");

        final ResponseEntity responseEntity;

        try {
            log.info("Tentando persistir o paper.");
            final Paper newPaper = paperService.save(paper);
            log.info(String.format("Persistência concluída com sucesso. Id do paper: %s.", newPaper.getId()));
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(paper.toDTO());
        } catch (final Exception e) {
            log.error(String.format("Não foi possível persistir o paper, mensagem de erro: %s. Classe: PaperController, método: save.", e.getMessage()));
            throw new PaperSaveException(e.getMessage());
        }

        return responseEntity;
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<PaperDTO>> findAll() {
        log.info("Iniciando o processo de busca de todos os papers.");

        final List<Paper> papers = paperService.findAll();

        log.info("Busca de todos os papers foi concluída com sucesso.");

        final List<PaperDTO> papersDTO = papers.stream().map(Paper::toDTO).collect(Collectors.toList());

        log.info(String.format("Dados encontrados: %s", papersDTO.stream().map(PaperDTO::toString).collect(Collectors.toList())));

        return ResponseEntity.status(HttpStatus.OK).body(papersDTO);
    }

    @GetMapping(value = "/findById")
    public ResponseEntity<PaperDTO> findById(@RequestParam final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(paperService.findById(id).toDTO());
    }
}
