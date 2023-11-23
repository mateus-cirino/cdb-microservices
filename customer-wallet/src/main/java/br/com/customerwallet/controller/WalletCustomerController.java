package br.com.customerwallet.controller;

import br.com.customerwallet.model.dto.WalletCustomerDTO;
import br.com.customerwallet.model.entity.WalletCustomer;
import br.com.customerwallet.service.WalletCustomerService;
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

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping(value = "/customer-wallet")
public class WalletCustomerController {

    @Autowired
    private WalletCustomerService walletCustomerService;

    @PostMapping(value = "/save")
    public ResponseEntity<WalletCustomerDTO> save(@Valid @RequestBody WalletCustomerDTO walletCustomerDTO) {
        log.info("Iniciando o processo de persistência do walletCustomer.");
        log.info(String.format("Dados que foram recebidos na request: %s", walletCustomerDTO.toString()));

        final WalletCustomer walletCustomer = walletCustomerDTO.fromDTO();

        log.info("Parse da entidade walletCustomer para walletCustomerDTO concluída com sucesso.");

        final WalletCustomerDTO newWalletCustomerDTO = walletCustomerService.save(walletCustomer).toDTO();

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", newWalletCustomerDTO.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(newWalletCustomerDTO);
    }

    @GetMapping(value = "/checkEnoughBalance")
    public ResponseEntity<Boolean> checkEnoughBalance(@RequestParam final Long customerId, @RequestParam final BigDecimal value) {
        log.info("Iniciando o processo de checagem de saldo suficiente do wallet do customer.");
        log.info(String.format("Dados que foram recebidos na request: customerId %s value %s", customerId.toString(), value.toString()));

        final Boolean hasEnoughBalance = walletCustomerService.checkEnoughBalance(customerId, value);

        log.info(String.format("Tem saldo suficiente: %s", hasEnoughBalance.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(hasEnoughBalance);
    }

    @GetMapping(value = "/findById")
    public ResponseEntity<WalletCustomerDTO> findById(@RequestParam final Long id) {
        log.info(String.format("Iniciando o processo de busca do walletCustomer com o id %s.", id));

        final WalletCustomerDTO walletCustomerFound = walletCustomerService.findById(id).toDTO();

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", walletCustomerFound.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(walletCustomerFound);
    }
}
