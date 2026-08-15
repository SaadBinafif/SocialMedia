package com.saad.socialmedia.controller;

import com.saad.socialmedia.Service.MessageService;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.models.Message;
import com.saad.socialmedia.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController
{
    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService)
    {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping("/api/messages/chat/{chatId}")
    public Message createMessage(@RequestBody Message req, @PathVariable Integer chatId,
                                 @RequestHeader("Authorization") String jwt) throws Exception
    {
        User user = userService.findUserByJwt(jwt);
        Message message = messageService.createMessage(user, chatId, req);

        return message;
    }

    @GetMapping("/api/messages/chat/{chatId}")
    public List<Message> findMessage(@PathVariable Integer chatId,
                                 @RequestHeader("Authorization") String jwt) throws Exception
    {
        User user = userService.findUserByJwt(jwt);
        List<Message> messages = messageService.findChatsMessages(chatId);

        return messages;
    }
}
