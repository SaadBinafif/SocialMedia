package com.saad.socialmedia.Service;

import com.saad.socialmedia.exception.UserException;
import com.saad.socialmedia.models.User;

import java.util.List;

public interface UserService
{
    public User registerUser(User user);

    public List<User> getAllUser();

    public User findUserById(Integer id) throws UserException;

    public User findUserByEmail(String email);

    public User followUser(Integer userId1, Integer userId2) throws UserException;

    public User updateUser(User user, Integer id) throws UserException;

    public List<User> searchUser(String query);

    public User unfollowUser(Integer userId1, Integer userId2) throws UserException;

    public User findUserByJwt(String jwt);
}
