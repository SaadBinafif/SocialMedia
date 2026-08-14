package com.saad.socialmedia.Service.Impl;

import com.saad.socialmedia.Repository.ChatRepository;
import com.saad.socialmedia.Service.ChatService;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.models.Chat;
import com.saad.socialmedia.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService
{

    private final ChatRepository chatRepository;
    public ChatServiceImpl(ChatRepository chatRepository)
    {
        this.chatRepository = chatRepository;
    }

    @Override
    public Chat createChat(User reqUser, User user2)
    {
        Chat isExist = chatRepository.findChatByUsersId(user2, reqUser);
        if (isExist != null)
        {
            return isExist;
        }

        Chat chat = new Chat();
        chat.getUsers().add(user2);
        chat.getUsers().add(reqUser);
        chat.setTimestamp(LocalDateTime.now());

        return chatRepository.save(chat);
    }

    @Override
    public Chat findChatById(Integer chatId) throws Exception {
        Optional<Chat> optional = chatRepository.findById(chatId);

        if(optional.isEmpty())
            throw new Exception("Chat not found with id : "+chatId);

        return optional.get();
    }

    @Override
    public List<Chat> findUsersChat(Integer userId)
    {
        return chatRepository.findByUsersId(userId);
    }
}
