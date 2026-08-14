package com.saad.socialmedia.Service;

import com.saad.socialmedia.models.Chat;
import com.saad.socialmedia.models.User;

import java.util.List;

public interface ChatService
{
    public Chat createChat(User reqUser, User user2);

    public Chat findChatById(Integer chatId) throws Exception;

    public List<Chat> findUsersChat(Integer userId);
}
