package br.com.cdbservice.controller;

import br.com.cdbservice.model.dto.WalletCDBDTO;
import br.com.cdbservice.model.entity.WalletCDB;
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

    @PostMapping(value = "/save")
    public ResponseEntity<WalletCDBDTO> save(@Valid @RequestBody WalletCDBDTO walletCDBDTO) {
        log.info("Iniciando o processo de persistência do walletCDB.");
        log.info(String.format("Dados que foram recebidos via @RequestBody: %s", walletCDBDTO.toString()));

        final WalletCDB walletCDB = walletCDBDTO.fromDTO();

        log.info("Parse da entidade walletCDB para walletCDBDTO concluída com sucesso.");

        final WalletCDBDTO newWalletCDBDTO = walletCDBService.save(walletCDB).toDTO();

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", newWalletCDBDTO.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(newWalletCDBDTO);
    }

    @GetMapping(value = "/findById")
    public ResponseEntity<WalletCDBDTO> findById(@RequestParam final Long id) {
        log.info(String.format("Iniciando o processo de busca do walletCDB com o id %s.", id));

        final WalletCDBDTO walletCDBFound = walletCDBService.findById(id).toDTO();

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", walletCDBFound.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(walletCDBFound);
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<WalletCDBDTO>> findAll() {
        log.info("Iniciando o processo de busca de todos os walletCDBs.");

        final List<WalletCDB> walletCDBs = walletCDBService.findAll();

        final List<WalletCDBDTO> walletCDBsDTO = walletCDBs.stream().map(WalletCDB::toDTO).collect(Collectors.toList());

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", walletCDBsDTO.stream().map(WalletCDBDTO::toString).collect(Collectors.toList())));

        return ResponseEntity.status(HttpStatus.OK).body(walletCDBsDTO);
    }
}
