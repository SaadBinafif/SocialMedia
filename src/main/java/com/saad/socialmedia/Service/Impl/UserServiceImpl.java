package com.saad.socialmedia.Service.Impl;

import com.saad.socialmedia.Repository.UserRepository;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.config.JwtUtils;
import com.saad.socialmedia.exception.UserException;
import com.saad.socialmedia.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user)
    {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setGender(user.getGender());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Integer id) throws UserException
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent())
            return user.get();

        throw new UserException("User Not Found with userId : "+id);
    }

    @Override
    public User findUserByEmail(String email)
    {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User followUser(Integer reqUserId, Integer userId2) throws UserException
    {
        User reqUser = findUserById(reqUserId);
        User user2 = findUserById(userId2);

        // Prevent self-follow
        if (reqUserId.equals(userId2))
        {
            throw new IllegalArgumentException("User cannot follow themselves.");
        }

        if (!reqUser.getFollowing().contains(userId2)) {
            reqUser.getFollowing().add(user2.getId());
        }

        if (!user2.getFollowers().contains(reqUserId)) {
            user2.getFollowers().add(reqUser.getId());
        }

        userRepository.save(reqUser);
        userRepository.save(user2);
        return reqUser;
    }

    @Override
    public User updateUser(User user, Integer id) throws UserException {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser==null)
            throw new UserException("User Not Exist with id : "+id);

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setGender(user.getGender());

        User updatedUser = userRepository.save(existingUser);
        return updatedUser;
    }

    @Override
    public List<User> searchUser(String query)
    {
        List<User> users = userRepository.searchUser(query);

        if (users.isEmpty())
        {
            throw new NoSuchElementException("No users found matching query: " + query);
        }

        return users;
    }

    @Override
    public User unfollowUser(Integer reqUserId, Integer userId2) throws UserException {
        User reqUser = findUserById(reqUserId);
        User user2 = findUserById(userId2);

        if (reqUserId.equals(userId2)) {
            throw new IllegalArgumentException("User cannot unfollow themselves.");
        }

        if (!reqUser.getFollowing().contains(userId2)) {
            throw new IllegalStateException("User is not following this person.");
        }

        reqUser.getFollowing().remove(userId2);
        user2.getFollowers().remove(reqUserId);

        userRepository.save(reqUser);
        userRepository.save(user2);

        return reqUser;
    }

    @Override
    public User findUserByJwt(String jwt)
    {
        String email = JwtUtils.getEmailFromJwtToken(jwt);

        User user = userRepository.findByEmail(email);
        return user;
    }
}
