package com.saad.socialmedia.controller;

import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.exception.UserException;
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
    public User getUserById(@PathVariable Integer id) throws UserException
    {
        return userService.findUserById(id);
    }

    @PutMapping("/api/users")
    public User updateUser(@RequestHeader("Authorization") String jwt, @RequestBody User user) throws UserException
    {
        User reqUser = userService.findUserByJwt(jwt);

        User updatedUser = userService.updateUser(user, reqUser.getId());
        return updatedUser;
    }

    @PutMapping("/api/users/follow/{id2}")
    public User followUserHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer id2) throws UserException
    {
        User reqUser = userService.findUserByJwt(jwt);
        User user = userService.followUser(reqUser.getId(), id2);
        return user;
    }

    @PutMapping("/api/users/unfollow/{id2}")
    public User unfollowUserHandler(@RequestHeader("Authorization") String jwt,
                                    @PathVariable Integer id2) throws UserException {
        User reqUser = userService.findUserByJwt(jwt);
        return userService.unfollowUser(reqUser.getId(), id2);
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
