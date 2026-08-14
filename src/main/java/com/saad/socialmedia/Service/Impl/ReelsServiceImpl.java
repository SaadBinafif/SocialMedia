package com.saad.socialmedia.Service.Impl;

import com.saad.socialmedia.Repository.ReelsRepository;
import com.saad.socialmedia.Service.ReelsService;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.models.Reels;
import com.saad.socialmedia.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReelsServiceImpl implements ReelsService
{
    private final ReelsRepository reelsRepository;
    private final UserService userService;
    public ReelsServiceImpl(ReelsRepository reelsRepository, UserService userService)
    {
        this.reelsRepository = reelsRepository;
        this.userService = userService;
    }

    @Override
    public Reels createReels(Reels reel, User user)
    {
        Reels createReel = new Reels();

        createReel.setTitle(reel.getTitle());
        createReel.setUser(user);
        createReel.setVideo(reel.getVideo());

        return reelsRepository.save(createReel);
    }

    @Override
    public List<Reels> findAllReels()
    {
        return reelsRepository.findAll();
    }

    @Override
    public List<Reels> findUsersReel(Integer userId) throws Exception
    {
        userService.findUserById(userId);
        return reelsRepository.findByUserId(userId);
    }
}
