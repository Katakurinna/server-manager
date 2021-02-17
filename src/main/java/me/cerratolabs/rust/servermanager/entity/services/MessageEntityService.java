package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rusrcon.events.messages.RustGenericMessage;
import me.cerratolabs.rust.servermanager.entity.entities.MessageEntity;
import me.cerratolabs.rust.servermanager.entity.repository.MessageEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageEntityService {

    @Autowired
    private MessageEntityRepository repository;

    public void save(RustGenericMessage m) {
        MessageEntity e = new MessageEntity();
        e.setMessage(m.getMessage());
        e.setStacktrace(m.getStacktrace());
        e.setType(m.getType());
        e.setIdentifier(m.getIdentifier());
        repository.save(e);
    }
}