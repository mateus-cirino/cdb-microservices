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

        log.info("Dados que foram recebidos na request: {}", walletCustomerDTO);

        final WalletCustomer walletCustomer = walletCustomerDTO.fromDTO();

        log.info("Parse da entidade walletCustomer para walletCustomerDTO concluída com sucesso.");

        final WalletCustomerDTO newWalletCustomerDTO = walletCustomerService.save(walletCustomer).toDTO();

        log.info("Dados que estão sendo devolvidos para a requisição: {}", newWalletCustomerDTO);

        return ResponseEntity.status(HttpStatus.OK).body(newWalletCustomerDTO);
    }

    @GetMapping(value = "/checkEnoughBalance")
    public ResponseEntity<Boolean> checkEnoughBalance(@RequestParam final Long customerId, @RequestParam final BigDecimal amount) {
        log.info("Iniciando o processo de checagem de saldo suficiente do wallet do customer.");

        log.info("Dados que foram recebidos na request: customerId {} amount {}", customerId, amount);

        final Boolean hasEnoughBalance = walletCustomerService.checkEnoughBalance(customerId, amount);

        log.info("Processo de verificacao de saldo suficiente concluido com sucesso");

        log.info("Dados que estão sendo devolvidos para a requisição: {}", hasEnoughBalance);

        return ResponseEntity.status(HttpStatus.OK).body(hasEnoughBalance);
    }

    @GetMapping(value = "/addAmountToBalance")
    public ResponseEntity<WalletCustomerDTO> addAmountToBalance(@RequestParam final Long customerId, @RequestParam final BigDecimal amount) {
        log.info("Iniciando o processo de adicao de uma quantia a wallet do customer.");

        log.info("Dados que foram recebidos na request: customerId {} amount {}", customerId, amount);

        final WalletCustomerDTO walletCustomerDTOUpdated = walletCustomerService.addAmountToBalance(customerId, amount).toDTO();

        log.info("Processo de adição de uma quantia a wallet do customer concluida com sucesso");

        log.info("Dados que estão sendo devolvidos para a requisição: {}", walletCustomerDTOUpdated);

        return ResponseEntity.status(HttpStatus.OK).body(walletCustomerDTOUpdated);
    }

    @GetMapping(value = "/findByCustomerId")
    public ResponseEntity<WalletCustomerDTO> findByCustomerId(@RequestParam final Long customerId) {
        log.info("Iniciando o processo de busca do walletCustomer do customer id {}.", customerId);

        final WalletCustomerDTO walletCustomerDTOFound = walletCustomerService.findByCustomerId(customerId).toDTO();

        log.info("Dados que estão sendo devolvidos para a requisição: {}", walletCustomerDTOFound);

        return ResponseEntity.status(HttpStatus.OK).body(walletCustomerDTOFound);
    }
}
