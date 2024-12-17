package com.openclassrooms.P3_Full_Stack_portail_locataire.service;

import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Message;
import com.openclassrooms.P3_Full_Stack_portail_locataire.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }
    public List<Message> getMessagesByRentalId(Long rentalId) {
        return messageRepository.findByRentalId(rentalId);
    }
}
