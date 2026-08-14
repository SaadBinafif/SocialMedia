package com.saad.socialmedia.controller;

import com.saad.socialmedia.Service.ChatService;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.models.Chat;
import com.saad.socialmedia.models.User;
import com.saad.socialmedia.request.CreateChatRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController
{
    private final ChatService chatService;
    private UserService userService;
    public ChatController(ChatService chatService, UserService userService)
    {
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping("/api/chats")
    public Chat createChat(@RequestBody CreateChatRequest request, @RequestHeader("Authorization") String jwt) throws Exception
    {
        User reqUser = userService.findUserByJwt(jwt);
        User user2 = userService.findUserById(request.getUserId());
        Chat chat = chatService.createChat(reqUser, user2);

        return chat;
    }

    @GetMapping("/api/chats")
    public List<Chat> findUsersChat(@RequestHeader("Authorization") String jwt)
    {
        User user = userService.findUserByJwt(jwt);

        List<Chat> chats = chatService.findUsersChat(user.getId());

        return chats;
    }
}
