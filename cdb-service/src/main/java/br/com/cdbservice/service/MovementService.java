package br.com.cdbservice.service;

import br.com.cdbservice.exception.WalletCDBNotFoundException;
import br.com.cdbservice.exception.WalletCDBSaveException;
import br.com.cdbservice.model.dto.WalletCustomerUpdateDTO;
import br.com.cdbservice.model.entity.WalletCDB;
import br.com.cdbservice.repository.WalletCDBRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MovementService {

    private static final String updateWalletTopic = "cgr.wallet.update";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void updateBalance(final WalletCustomerUpdateDTO walletCustomerUpdateDTO) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();

        kafkaTemplate.send(updateWalletTopic, objectMapper.writeValueAsString(walletCustomerUpdateDTO));
    }
}
