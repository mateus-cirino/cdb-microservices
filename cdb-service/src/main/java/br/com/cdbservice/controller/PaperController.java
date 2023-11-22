package br.com.cdbservice.controller;

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

        log.info("Parse da entidade paper para paperDTO concluída com sucesso.");

        final PaperDTO newPaperDTO = paperService.save(paper).toDTO();

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", newPaperDTO.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(newPaperDTO);
    }

    @GetMapping(value = "/findById")
    public ResponseEntity<PaperDTO> findById(@RequestParam final Long id) {
        log.info(String.format("Iniciando o processo de busca do paper com o id %s.", id));

        final PaperDTO paperFound = paperService.findById(id).toDTO();

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", paperFound.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(paperFound);
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<PaperDTO>> findAll() {
        log.info("Iniciando o processo de busca de todos os papers.");

        final List<Paper> papers = paperService.findAll();

        final List<PaperDTO> papersDTO = papers.stream().map(Paper::toDTO).collect(Collectors.toList());

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", papersDTO.stream().map(PaperDTO::toString).collect(Collectors.toList())));

        return ResponseEntity.status(HttpStatus.OK).body(papersDTO);
    }
}
