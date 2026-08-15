package com.saad.socialmedia.Service;

import com.saad.socialmedia.models.Chat;
import com.saad.socialmedia.models.Message;
import com.saad.socialmedia.models.User;

import java.util.List;

public interface MessageService
{
    public Message createMessage(User user, Integer chatId, Message req) throws Exception;

    public List<Message> findChatsMessages(Integer chatId) throws Exception;
}
