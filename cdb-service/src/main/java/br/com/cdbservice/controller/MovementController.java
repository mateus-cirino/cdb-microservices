package br.com.cdbservice.controller;

import br.com.cdbservice.exception.NotHasEnoughBalanceException;
import br.com.cdbservice.model.dto.WalletCDBDTO;
import br.com.cdbservice.model.entity.Paper;
import br.com.cdbservice.model.entity.WalletCDB;
import br.com.cdbservice.service.PaperService;
import br.com.cdbservice.service.WalletCDBService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping(value = "/cdb-service/movement")
public class MovementController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private WalletCDBService walletCDBService;

    private WebClient webClientCustomer;

    @PostConstruct
    private void postConstruct() {
        webClientCustomer = WebClient.builder().baseUrl("http://localhost:8080").build();
    }

    @GetMapping(value = "/buy")
    public ResponseEntity<Boolean> buy(@RequestParam final Long customerId, @RequestParam final Long paperId, @RequestParam final BigDecimal amount) {
        final Paper paper = paperService.findById(paperId);

        final BigDecimal value = paper.getValue().multiply(amount);

        final Boolean hasEnoughBalance = webClientCustomer.get()
                .uri(uriBuilder -> uriBuilder.path("/customer/checkEnoughBalance").queryParam("customerId", customerId).queryParam("value", value).build())
                .retrieve()
                .toEntity(Boolean.class)
                .block()
                .getBody();

        if (hasEnoughBalance) {
            // TODO: 23/11/2023 E se comprar um paper duas vezes?
            final WalletCDBDTO walletCDBDTO = new WalletCDBDTO();
            walletCDBDTO.setPaper(paper.toDTO());
            walletCDBDTO.setAmount(amount);
            walletCDBDTO.setCustomerId(customerId);

            walletCDBService.save(walletCDBDTO.fromDTO());
            // TODO: 23/11/2023 chama o tópico do kafka para atualizar o balance do customer
        } else {
            throw new NotHasEnoughBalanceException(String.format("O customer de id %s não possuí saldo suficiente para a comprar a quantidade de %s do paper de id %s. Valor total da compra do(s) paper(s) %s.", customerId, amount, paperId, value));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
    }

    @GetMapping(value = "/sell")
    public ResponseEntity<Boolean> sell(@RequestParam final Long customerId, @RequestParam final Long paperId, @RequestParam final Long amount) {
        final Paper paper = paperService.findById(paperId);

        // chama o tópico do kafka para atualizar o balance do customer

        return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
    }
}
