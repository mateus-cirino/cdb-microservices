package br.com.cdbservice.controller;

import br.com.cdbservice.exception.NotHasEnoughBalanceException;
import br.com.cdbservice.exception.SellMorePaperThanHaveException;
import br.com.cdbservice.exception.WalletCDBNotFoundException;
import br.com.cdbservice.model.dto.WalletCustomerUpdateDTO;
import br.com.cdbservice.model.entity.Paper;
import br.com.cdbservice.model.entity.WalletCDB;
import br.com.cdbservice.model.enumerated.MovementType;
import br.com.cdbservice.service.MovementService;
import br.com.cdbservice.service.PaperService;
import br.com.cdbservice.service.WalletCDBService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/cdb-service/movement")
public class MovementController {

    @Value("${customer.url}")
    private String customerUrl;

    @Autowired
    private MovementService movementService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private WalletCDBService walletCDBService;

    private WebClient webClientCustomer;

    @PostConstruct
    private void postConstruct() {
        webClientCustomer = WebClient.builder().baseUrl(customerUrl).build();
    }

    @GetMapping(value = "/buy")
    public ResponseEntity<Boolean> buy(@RequestParam final Long customerId, @RequestParam final Long paperId, @RequestParam final BigDecimal amount) throws JsonProcessingException {
        final Paper paper = paperService.findById(paperId);

        final BigDecimal paperValueMultiplyAmount = paper.getValue().multiply(amount);

        final Boolean hasEnoughBalance = Objects.requireNonNull(webClientCustomer.get()
                        .uri(uriBuilder -> uriBuilder.path("/customer/checkEnoughBalance").queryParam("customerId", customerId).queryParam("amount", paperValueMultiplyAmount).build())
                        .retrieve()
                        .toEntity(Boolean.class)
                        .block())
                        .getBody();

        if (hasEnoughBalance) {
            WalletCDB walletCDB;
            try {
                log.info("Verificando se o customer de id {} já possui uma wallet cdb com o paper de id {}", customerId, paperId);

                walletCDB = walletCDBService.findByCustomerIdAndPaperId(customerId, paperId);
                walletCDB.setAmount(Optional.ofNullable(walletCDB).map(WalletCDB::getAmount).map(it -> it.add(amount)).orElse(BigDecimal.ZERO));

                log.info("Possui, não será criada outra wallet cdb, só sera atualizado a quantidade de paper nessa wallet");
            } catch (final WalletCDBNotFoundException e) {
                walletCDB = new WalletCDB();
                walletCDB.setPaper(paper);
                walletCDB.setAmount(amount);
                walletCDB.setCustomerId(customerId);

                log.info("Não foi encontrada uma wallet cdb, uma nova será criada.");
            }

            walletCDBService.save(walletCDB);

            final WalletCustomerUpdateDTO walletCustomerUpdateDTO = new WalletCustomerUpdateDTO();
            walletCustomerUpdateDTO.setCustomerId(customerId);
            walletCustomerUpdateDTO.setAmount(paperValueMultiplyAmount);
            walletCustomerUpdateDTO.setMovementType(MovementType.BUY);

            movementService.updateBalance(walletCustomerUpdateDTO);
        } else {
            throw new NotHasEnoughBalanceException(String.format("O customer de id %s não possuí saldo suficiente para a comprar a quantidade de %s do paper de id %s. Valor total da compra do(s) paper(s) %s.", customerId, amount, paperId, paperValueMultiplyAmount));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
    }

    @GetMapping(value = "/sell")
    public ResponseEntity<Boolean> sell(@RequestParam final Long customerId, @RequestParam final Long paperId, @RequestParam final BigDecimal amount) throws JsonProcessingException {
        final Paper paper = paperService.findById(paperId);

        final WalletCDB walletCDB = walletCDBService.findByCustomerIdAndPaperId(customerId, paperId);

        if (walletCDB.getAmount().compareTo(amount) < 0) {
            throw new SellMorePaperThanHaveException(String.format("O customer de id %s tem %s papers de cdb e está tentando vender %s. Está tentando vender mais do que tem.", customerId, walletCDB.getAmount(), amount));
        }

        if (walletCDB.getAmount().compareTo(amount) > 0) {
            walletCDB.setAmount(walletCDB.getAmount().subtract(amount));

            walletCDBService.save(walletCDB);
        } else {
            walletCDBService.delete(walletCDB);
        }

        final WalletCustomerUpdateDTO walletCustomerUpdateDTO = new WalletCustomerUpdateDTO();
        walletCustomerUpdateDTO.setCustomerId(customerId);
        walletCustomerUpdateDTO.setAmount(paper.getValue().multiply(amount));
        walletCustomerUpdateDTO.setMovementType(MovementType.SELL);

        movementService.updateBalance(walletCustomerUpdateDTO);

        return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
    }
}
