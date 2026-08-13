package com.saad.socialmedia.Service.Impl;

import com.saad.socialmedia.Repository.UserRepository;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.config.JwtUtils;
import com.saad.socialmedia.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user)
    {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Integer id) throws Exception
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent())
            return user.get();

        throw new Exception("User Not Found with userId : "+id);
    }

    @Override
    public User findUserByEmail(String email)
    {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User followUser(Integer userId1, Integer userId2) throws Exception
    {
        User user1 = findUserById(userId1);
        User user2 = findUserById(userId2);

        // Prevent self-follow
        if (userId1.equals(userId2))
        {
            throw new IllegalArgumentException("User cannot follow themselves.");
        }

        if (!user1.getFollowing().contains(userId2)) {
            user1.getFollowing().add(userId2);
        }

        if (!user2.getFollowers().contains(userId1)) {
            user2.getFollowers().add(userId1);
        }

        userRepository.save(user1);
        userRepository.save(user2);
        return user1;
    }

    @Override
    public User updateUser(User user, Integer id) throws Exception {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser==null)
            throw new Exception("User Not Exist with id : "+id);

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
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
    public User unfollowUser(Integer userId1, Integer userId2) throws Exception
    {
        User user1 = findUserById(userId1);
        User user2 = findUserById(userId2);

        if(userId1.equals(userId2))
            throw new IllegalArgumentException("User Cannot Unfollow themeselves.");

        // Check if user1 is actually following user2
        if (!user1.getFollowing().contains(userId2))
        {
            throw new IllegalStateException("User is not following this person.");
        }

        user1.getFollowing().remove(userId2);
        user2.getFollowers().remove(userId1);

        userRepository.save(user1);
        userRepository.save(user2);

        return user1;
    }

    @Override
    public User findUserByJwt(String jwt)
    {
        String email = JwtUtils.getEmailFromJwtToken(jwt);

        User user = userRepository.findByEmail(email);
        return user;
    }
}
