package com.example.demo.repositories;

import com.example.demo.entities.Transaction;
import com.example.demo.enums.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM transactions t WHERE t.sender.id = :userId OR t.receiver.id = :userId")
    Page<Transaction> findByUserParticipation(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT t FROM transactions t WHERE ((t.sender.id = :userId OR t.receiver.id = :userId) and t.status = :status)")
    Page<Transaction> findByUserParticipationAndStatus(@Param("userId") Long userId, @Param("status") TransactionStatus status, Pageable pageable);
}
