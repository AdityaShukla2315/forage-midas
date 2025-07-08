package com.jpmc.midascore.kafka;

import com.jpmc.midascore.config.KafkaProperties;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.model.TransactionRecord;
import com.jpmc.midascore.model.User;
import com.jpmc.midascore.model.Incentive;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.repository.TransactionRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionListener {

    private final UserRepository userRepository;
    private final TransactionRecordRepository recordRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public TransactionListener(UserRepository userRepository, TransactionRecordRepository recordRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.restTemplate = restTemplate;
    }

    @KafkaListener(topics = "#{@kafkaProperties.transactionTopic}", groupId = "midas-core")
    public void handleTransaction(Transaction tx) {
        Optional<User> senderOpt = userRepository.findById(String.valueOf(tx.getSenderId()));
        Optional<User> recipientOpt = userRepository.findById(String.valueOf(tx.getRecipientId()));

        if (senderOpt.isEmpty() || recipientOpt.isEmpty()) return;

        User sender = senderOpt.get();
        User recipient = recipientOpt.get();

        if (sender.getBalance() >= tx.getAmount()) {
            // Step 1: Call incentive API
            Incentive incentive = restTemplate.postForObject(
                    "http://localhost:8080/incentive",
                    tx,
                    Incentive.class
            );

            double incentiveAmount = (incentive != null) ? incentive.getAmount() : 0.0;

            // Step 2: Update balances
            sender.setBalance(sender.getBalance() - tx.getAmount());
            recipient.setBalance(recipient.getBalance() + tx.getAmount() + incentiveAmount);

            userRepository.save(sender);
            userRepository.save(recipient);

            // Step 3: Persist TransactionRecord
            TransactionRecord record = new TransactionRecord();
            record.setSender(sender);
            record.setRecipient(recipient);
            record.setAmount(tx.getAmount());
            record.setIncentive(incentiveAmount);
            record.setTimestamp(LocalDateTime.now());

            recordRepository.save(record);
        }
    }
} 