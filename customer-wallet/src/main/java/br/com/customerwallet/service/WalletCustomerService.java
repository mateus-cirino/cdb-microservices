package br.com.customerwallet.service;

import br.com.customerwallet.exception.WalletCustomerNotFoundException;
import br.com.customerwallet.exception.WalletCustomerSaveException;
import br.com.customerwallet.model.dto.WalletCustomerUpdateDTO;
import br.com.customerwallet.model.entity.WalletCustomer;
import br.com.customerwallet.repository.WalletCustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class WalletCustomerService {

    private static final String updateWalletTopic = "${kafka.topic.update-wallet.url}";
    @Autowired
    private WalletCustomerRepository walletCustomerRepository;

    public WalletCustomer save(final WalletCustomer walletCustomer) {
        try {
            log.info("Tentando persistir o walletCustomer.");

            final WalletCustomer newWalletCustomer = walletCustomerRepository.save(walletCustomer);

            walletCustomer.setId(newWalletCustomer.getId());

            log.info(String.format("Persistência concluída com sucesso. Id do walletCustomer: %s.", walletCustomer.getId()));
        } catch (final Exception e) {
            log.error(String.format("Não foi possível persistir o walletCustomer, mensagem de erro: %s. Classe: %s, método: save.", e.getMessage(), WalletCustomerService.class.getSimpleName()));

            throw new WalletCustomerSaveException(e.getMessage());
        }

        return walletCustomer;
    }

    public Boolean checkEnoughBalance(final Long customerId, final BigDecimal value) {
        log.info("Tentando verificar se o customer tem saldo suficiente.");

        final Boolean hasEnoughBalance = walletCustomerRepository.findByCustomerIdAndBalanceIsGreaterThanEqual(customerId, value).isPresent();

        log.info("Processo de verificacao se o customer tem saldo suficiente concluido com sucesso.");

        return hasEnoughBalance;
    }

    public WalletCustomer addAmountToBalance(final Long customerId, final BigDecimal amount) {
        log.info("Tentando adicionar uma quantia ao wallet do customer.");

        walletCustomerRepository.updateBalance(customerId, amount);

        log.info("Processo de adicao de uma quantia ao wallet do customer concluido com sucesso.");

        return walletCustomerRepository.findByCustomerId(customerId).orElseThrow(() -> new WalletCustomerNotFoundException(String.format("Não foi encontrada uma wallet para o customer id %s", customerId)));
    }

    @KafkaListener(topics = updateWalletTopic)
    public void updateBalance(String message) throws JsonProcessingException {
        log.info("Recebendo uma solicitacao de update da wallet do customer via topico na url {}.", updateWalletTopic);

        final ObjectMapper objectMapper = new ObjectMapper();

        final WalletCustomerUpdateDTO walletCustomerUpdateDTO = objectMapper.readValue(message, WalletCustomerUpdateDTO.class);

        log.info("WalletCustomerUpdateDTO recebido pela mensagem {}.", walletCustomerUpdateDTO);

        walletCustomerRepository.updateBalance(walletCustomerUpdateDTO.getCustomerId(), walletCustomerUpdateDTO.getAmount());

        log.info("Update concluido com sucesso");
    }

    public WalletCustomer findByCustomerId(final Long customerId) {
        log.info("Tentando buscar o walletCustomer do customer de id {}.", customerId);

        final Optional<WalletCustomer> optWalletCustomer = walletCustomerRepository.findByCustomerId(customerId);

        final WalletCustomer walletCustomerFound;

        if (optWalletCustomer.isPresent()) {
            log.info(String.format("WalletCustomer do customer id %s encontrado com sucesso.", customerId));

            walletCustomerFound = optWalletCustomer.get();
        } else {
            throw new WalletCustomerNotFoundException(String.format("O walletCustomer do customer id %s não existe", customerId));
        }

        return walletCustomerFound;
    }
}
