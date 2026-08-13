package com.saad.socialmedia.controller;

import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController
{
    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public List<User> getAllUsers()
    {
        List<User> users = userService.getAllUser();
        return users;
    }

    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable Integer id) throws Exception
    {
        return userService.findUserById(id);
    }

    @PutMapping("/api/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Integer id) throws Exception
    {
        User updatedUser = userService.updateUser(user, id);
        return updatedUser;
    }

    @PutMapping("/api/users/follow/{id1}/{id2}")
    public User followUserHandler(@PathVariable Integer id1 ,@PathVariable Integer id2) throws Exception
    {
        User user = userService.followUser(id1, id2);
        return user;
    }

    @PutMapping("/api/users/unfollow/{id1}/{id2}")
    public User unfollowUserHandler(@PathVariable Integer id1, @PathVariable Integer id2) throws Exception
    {
        return userService.unfollowUser(id1, id2);
    }

    @GetMapping("/api/users/search")
    public List<User> searchUsers(@RequestParam("query") String query)
    {
        List<User> users = userService.searchUser(query);

        return users;
    }

    @GetMapping("/api/users/profile")
    public User getUserFromToken(@RequestHeader("Authorization") String jwt)
    {
        User user = userService.findUserByJwt(jwt);

        user.setPassword(null);

        return user;
    }
}
