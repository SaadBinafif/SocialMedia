package com.saad.socialmedia.Service.Impl;

import com.saad.socialmedia.Repository.ChatRepository;
import com.saad.socialmedia.Repository.MessageRepository;
import com.saad.socialmedia.Service.ChatService;
import com.saad.socialmedia.Service.MessageService;
import com.saad.socialmedia.models.Chat;
import com.saad.socialmedia.models.Message;
import com.saad.socialmedia.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService
{
    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final ChatRepository chatRepository;
    public MessageServiceImpl(MessageRepository messageRepository, ChatService chatService, ChatRepository chatRepository)
    {
        this.messageRepository = messageRepository;
        this.chatService = chatService;
        this.chatRepository = chatRepository;
    }

    @Override
    public Message createMessage(User user, Integer chatId, Message req) throws Exception
    {

        Chat chat = chatService.findChatById(chatId);

        Message message = new Message();

        message.setChat(chat);
        message.setContent(req.getContent());
        message.setImage(req.getImage());
        message.setUser(user);
        message.setTimestamp(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);
        chat.getMessages().add(savedMessage);
        chatRepository.save(chat);
        return savedMessage;
    }

    @Override
    public List<Message> findChatsMessages(Integer chatId) throws Exception
    {
        Chat chat = chatService.findChatById(chatId);

        return messageRepository.findByChatId(chatId);
    }
}
