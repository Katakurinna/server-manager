package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.entity.entities.MessageEntity;
import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.jentity.ChatLog;
import me.cerratolabs.rust.servermanager.entity.jentity.Message;
import me.cerratolabs.rust.servermanager.entity.repository.MessageEntityRepository;
import me.cerratolabs.rustrcon.events.messages.RustGenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageEntityService {

    @Autowired
    private MessageEntityRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private WipeEntityService wipeServer;

    public void save(RustGenericMessage m, ServerEntity server) {
        MessageEntity e = new MessageEntity();
        e.setMessage(m.getMessage());
        e.setStacktrace(m.getStacktrace());
        e.setType(m.getType());
        e.setIdentifier(m.getIdentifier());
        e.setServer(server);
        e.setWipe(wipeServer.findWipeByServer(server));
        repository.save(e);
    }

    public Object getChat(String name) {
        Query query = entityManager.createNativeQuery("SELECT message, timestamp FROM `message_entity` WHERE message LIKE '%" + name + "%' ORDER BY `timestamp` ASC");
        List<Object[]> resultList = query.getResultList();
        return new ChatLog(resultList.stream().map(p -> parseToMessage(p)).collect(Collectors.toList()));
    }

    private Message parseToMessage(Object[] obj) {
        return new Message(obj[0].toString(), obj[1].toString());
    }
}