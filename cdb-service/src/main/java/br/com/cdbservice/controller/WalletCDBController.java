package br.com.cdbservice.controller;

import br.com.cdbservice.exception.WalletCDBSaveException;
import br.com.cdbservice.model.dto.WalletCDBDTO;
import br.com.cdbservice.model.entity.WalletCDB;
import br.com.cdbservice.service.PaperService;
import br.com.cdbservice.service.WalletCDBService;
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
@RequestMapping(value = "/cdb-service/wallet-cdb")
public class WalletCDBController {

    @Autowired
    private WalletCDBService walletCDBService;

    @Autowired
    private PaperService paperService;

    @PostMapping(value = "/save")
    public ResponseEntity<WalletCDBDTO> save(@Valid @RequestBody WalletCDBDTO walletCDBDTO) {
        log.info("Iniciando o processo de persistência do walletCDB.");
        log.info(String.format("Dados que foram recebidos via @RequestBody: %s", walletCDBDTO.toString()));

        final WalletCDB walletCDB = walletCDBDTO.fromDTO();

        log.info("Criação da entidade walletCDB concluída com sucesso.");

        final ResponseEntity responseEntity;

        try {
            log.info("Tentando persistir o walletCDB.");
            final WalletCDB newWalletCDB = walletCDBService.save(walletCDB);
            newWalletCDB.setPaper(paperService.findById(newWalletCDB.getPaper().getId()));
            log.info(String.format("Persistência concluída com sucesso. Id do walletCDB: %s.", newWalletCDB.getId()));
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(walletCDB.toDTO());
        } catch (final Exception e) {
            log.error(String.format("Não foi possível persistir o walletCDB, mensagem de erro: %s. Classe: WalletCDBController, método: save.", e.getMessage()));
            throw new WalletCDBSaveException(e.getMessage());
        }

        return responseEntity;
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<WalletCDBDTO>> findAll() {
        log.info("Iniciando o processo de busca de todos os walletCDBs.");

        final List<WalletCDB> walletCDBs = walletCDBService.findAll();

        log.info("Busca de todos os walletCDBs foi concluída com sucesso.");

        final List<WalletCDBDTO> walletCDBsDTO = walletCDBs.stream().map(WalletCDB::toDTO).collect(Collectors.toList());

        log.info(String.format("Dados encontrados: %s", walletCDBsDTO.stream().map(WalletCDBDTO::toString).collect(Collectors.toList())));

        return ResponseEntity.status(HttpStatus.OK).body(walletCDBsDTO);
    }

    @GetMapping(value = "/findById")
    public ResponseEntity<WalletCDBDTO> findById(@RequestParam final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(walletCDBService.findById(id).toDTO());
    }
}
