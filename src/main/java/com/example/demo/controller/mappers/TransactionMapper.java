package com.example.demo.controller.mappers;

import com.example.demo.controller.data.requests.TransactionRequestDTO;
import com.example.demo.controller.data.responses.TransactionResponseDTO;
import com.example.demo.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "status", constant = "NEW")
    @Mapping(source = "senderId", target = "sender.id")
    @Mapping(source = "receiverId", target = "receiver.id")
    Transaction map(TransactionRequestDTO dto);
    TransactionResponseDTO map(Transaction entity);
}