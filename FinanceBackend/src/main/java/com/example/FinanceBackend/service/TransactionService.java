package com.example.FinanceBackend.service;

import com.example.FinanceBackend.model.Transaction;
import com.example.FinanceBackend.model.User;
import com.example.FinanceBackend.repository.TransactionRepository;
import com.example.FinanceBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction addTransaction(Long userId, Transaction transaction) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            transaction.setUser(userOptional.get());
            return transactionRepository.save(transaction);
        }
        throw new RuntimeException("Usuário não encontrado!");
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transaction.setAmount(updatedTransaction.getAmount());
                    transaction.setType(updatedTransaction.getType());
                    transaction.setCategory(updatedTransaction.getCategory());
                    transaction.setDescription(updatedTransaction.getDescription());
                    transaction.setDate(updatedTransaction.getDate());
                    return transactionRepository.save(transaction);
                })
                .orElseThrow(() -> new RuntimeException("Transação não encontrada!"));
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}

