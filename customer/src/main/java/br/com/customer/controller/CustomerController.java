package br.com.customer.controller;

import br.com.customer.model.dto.CustomerDTO;
import br.com.customer.model.dto.WalletCDBDTO;
import br.com.customer.model.dto.WalletCustomerDTO;
import br.com.customer.model.entity.Customer;
import br.com.customer.service.CustomerService;
import jakarta.annotation.PostConstruct;
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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private WebClient webClientCustomerWallet;

    private WebClient webClientCDBService;

    @PostConstruct
    private void postConstruct() {
        webClientCustomerWallet = WebClient.builder().baseUrl("http://localhost:8082").build();
        webClientCDBService = WebClient.builder().baseUrl("http://localhost:8081").build();
    }

    @PostMapping(value = "/save")
    public ResponseEntity<CustomerDTO> save(@Valid @RequestBody CustomerDTO customerDTO) {
        log.info("Iniciando o processo de persistência do customer.");
        log.info(String.format("Dados que foram recebidos na request: %s", customerDTO.toString()));

        final Customer customer = customerDTO.fromDTO();

        log.info("Parse da entidade customer para customerDTO concluída com sucesso.");

        final CustomerDTO newCustomerDTO = customerService.save(customer).toDTO();

        final WalletCustomerDTO walletCustomerDTO = new WalletCustomerDTO();
        walletCustomerDTO.setCustomerId(newCustomerDTO.getId());
        walletCustomerDTO.setBalance(BigDecimal.ZERO);

        log.info("Solicitando ao microserviço customer-wallet a criação da wallet para o customer.");

        final WalletCustomerDTO newWalletCustomerDTO = webClientCustomerWallet.post()
                .uri("/customer-wallet/save")
                .body(BodyInserters.fromValue(walletCustomerDTO))
                .retrieve()
                .toEntity(WalletCustomerDTO.class)
                .block()
                .getBody();

        log.info(String.format("Solicitacao concluida com sucesso, dados da wallet do customer que foi criada: %s", newWalletCustomerDTO.toString()));

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", newCustomerDTO));

        return ResponseEntity.status(HttpStatus.OK).body(newCustomerDTO);
    }

    @GetMapping(value = "/checkEnoughBalance")
    public ResponseEntity<Boolean> checkEnoughBalance(@RequestParam final Long customerId, @RequestParam final BigDecimal value) {
        log.info("Iniciando o processo de checagem de saldo suficiente do wallet do customer.");
        log.info(String.format("Dados que foram recebidos na request: customerId %s value %s", customerId.toString(), value.toString()));

        log.info("Solicitando ao microserviço customer-wallet a verificação de saldo suficiente.");


        final Boolean hasEnoughBalance = webClientCustomerWallet.get()
                .uri(uriBuilder -> uriBuilder.path("/customer-wallet/checkEnoughBalance").queryParam("customerId", customerId).queryParam("value", value).build())
                .retrieve()
                .toEntity(Boolean.class)
                .block()
                .getBody();

        log.info(String.format("Solicitacao concluida com sucesso, tem saldo suficiente: %s", hasEnoughBalance.toString()));

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", hasEnoughBalance));

        return ResponseEntity.status(HttpStatus.OK).body(hasEnoughBalance);
    }

    @GetMapping(value = "/addAmountToBalance")
    public ResponseEntity<WalletCustomerDTO> addAmountToBalance(@RequestParam final Long customerId, @RequestParam final BigDecimal amount) {

        final WalletCustomerDTO walletCustomerDTOUpdated = webClientCustomerWallet.get()
                .uri(uriBuilder -> uriBuilder.path("/customer-wallet/addAmountToBalance").queryParam("customerId", customerId).queryParam("amount", amount).build())
                .retrieve()
                .toEntity(WalletCustomerDTO.class)
                .block()
                .getBody();

        return ResponseEntity.status(HttpStatus.OK).body(walletCustomerDTOUpdated);
    }

    @GetMapping(value = "/findById")
    public ResponseEntity<CustomerDTO> findById(@RequestParam final Long id) {
        log.info(String.format("Iniciando o processo de busca do customer com o id %s,sua carteira e seus cdbs.", id));

        final CustomerDTO customerFound = customerService.findById(id).toDTO();

        log.info("Solicitando ao microserviço customer-wallet a busca do wallet do customer.");

        final WalletCustomerDTO walletCustomerDTO = webClientCustomerWallet.get()
                .uri(uriBuilder -> uriBuilder.path("/customer-wallet/findById").queryParam("id", id).build())
                .retrieve()
                .toEntity(WalletCustomerDTO.class)
                .block()
                .getBody();

        log.info(String.format("Solicitacao concluida com sucesso, wallet do customer: %s", walletCustomerDTO.toString()));

        customerFound.setWalletCustomerDTO(walletCustomerDTO);

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", customerFound));

        log.info("Solicitando ao microserviço cdb-service a busca de todos os cdbs do customer.");

        final List<WalletCDBDTO> walletCDBDTOList = webClientCDBService.get()
                .uri(uriBuilder -> uriBuilder.path("/cdb-service/wallet-cdb/findAllByCustomerId").queryParam("customerId", id).build())
                .retrieve()
                .toEntityList(WalletCDBDTO.class)
                .block()
                .getBody();

        log.info(String.format("Solicitacao concluida com sucesso, cdbs do customer: %s", walletCDBDTOList.toString()));

        customerFound.setWalletCDBDTOList(walletCDBDTOList);

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", customerFound));

        return ResponseEntity.status(HttpStatus.OK).body(customerFound);
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<CustomerDTO>> findAll() {
        log.info("Iniciando o processo de busca de todos os customers.");

        final List<Customer> customers = customerService.findAll();

        final List<CustomerDTO> customersDTO = customers.stream().map(Customer::toDTO).collect(Collectors.toList());

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", customersDTO.stream().map(CustomerDTO::toString).collect(Collectors.toList())));

        return ResponseEntity.status(HttpStatus.OK).body(customersDTO);
    }
}
